package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Locale;

import salon.octadevtn.sa.salon.Adaptor.ExploreAdaptor;
import salon.octadevtn.sa.salon.Api.getMostPopular;
import salon.octadevtn.sa.salon.Models.MostPopular;
import salon.octadevtn.sa.salon.Models.Mostpopulair;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ExploreAdaptor adapter;
    ArrayList<Mostpopulair> activityList;
    LinearLayout nodata;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        activityList = new ArrayList<>();
        nodata = v.findViewById(R.id.nodata);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) v.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        adapter = new ExploreAdaptor(activityList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                runLayoutAnimation(recyclerView);
                load();
            }

        });
        swipeRefreshLayout.setColorSchemeResources(
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                load();
            }
        });


        adapter.notifyDataSetChanged();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    public void load() {
        nodata.setVisibility(View.GONE);
        new getMostPopular().mostpoplar(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    activityList.clear();
                    activityList.addAll(((MostPopular) result).getMostpopulair());

                    runLayoutAnimation(recyclerView);
                    swipeRefreshLayout.setRefreshing(false);
                    if (activityList.size() == 0) {
                        nodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    showError();
                    nodata.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";
                swipeRefreshLayout.setRefreshing(false);
                showError();
                nodata.setVisibility(View.VISIBLE);


            }

            @Override
            public void OnError(String message) {
                swipeRefreshLayout.setRefreshing(false);
                nodata.setVisibility(View.VISIBLE);

                showError();

            }

            @Override
            public void onFinish() {
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }

    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
        int gravity = LEFT;
        if (Locale.getDefault().getLanguage() == "ar")
            gravity = RIGHT;
        if (ExploreFragment.this.isVisible()) {
            Alerter a = Alerter.create(getActivity())
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))

                    .setIcon(getResources().getDrawable(R.drawable.wifisi))
                    .setDuration(2000)
                    .enableSwipeToDismiss()
                    .setContentGravity(gravity)
                    .setBackgroundColorRes(R.color.color2)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(false);

                            Alerter.hide();
                        }

                    });
            a.show();
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
