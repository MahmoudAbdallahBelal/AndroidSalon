package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Adaptor.FavoriteAdaptor;
import salon.octadevtn.sa.salon.Api.FavoriteAPI;
import salon.octadevtn.sa.salon.Models.Favorite;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    LinearLayout lin;
    RecyclerView recyclerView;
    FavoriteAdaptor mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Favorite> favList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void onBackPressed() {
        //Pop Fragments off backstack and do your other checks
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
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, v);
        title.setTypeface(MyApplication.type_jf_medium);
        back.setTypeface(MyApplication.type_jf_regular);
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ((TextView) v.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

        if (Build.VERSION.SDK_INT < 16) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        lin = (LinearLayout) v.findViewById(R.id.nodata);

        mAdapter = new FavoriteAdaptor(favList, getActivity(), recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void load() {
        lin.setVisibility(View.GONE);

        new FavoriteAPI().getpromotion(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    swipeRefreshLayout.setRefreshing(false);

                    Favorite[] ob = (Favorite[]) result;
                    favList.clear();
                    for (int i = 0; i < ob.length; i++) {
                        favList.add(ob[i]);
                        runLayoutAnimation(recyclerView);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                    if (favList.size() == 0)
                        lin.setVisibility(View.VISIBLE);

                } else showError();
            }

            @Override
            public void onFailure(Object result) {
                showError();
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                showError();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    public void showError() {
        int gravity = LEFT;
        if (Locale.getDefault().getLanguage() == "ar")
            gravity = RIGHT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (FavoriteFragment.this.isVisible())
                Alerter.create(getActivity())
                        .setTitle(getResources().getString(R.string.erreur1))
                        .setText(getResources().getString(R.string.erreur2))
                        .setIcon(R.drawable.refresh_wifi_signal)
                        .setContentGravity(gravity)
                        .setDuration(2000)
                        .enableSwipeToDismiss()

                        .setBackgroundColorRes(R.color.color2)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swipeRefreshLayout.setRefreshing(true);

                                load();

                            }

                        })
                        .show();
        } else if (FavoriteFragment.this.isVisible())
            Alerter.create(getActivity())
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))
                    .setContentGravity(gravity)
                    .setDuration(2000)
                    .enableSwipeToDismiss()
                    .setIcon(getResources().getDrawable(R.drawable.wifisi))
                    .setBackgroundColorRes(R.color.color2)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);

                            load();

                        }

                    })
                    .show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
