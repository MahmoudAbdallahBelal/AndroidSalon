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
import android.widget.TextView;

import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Locale;

import salon.octadevtn.sa.salon.Adaptor.AdaptorService_Category;
import salon.octadevtn.sa.salon.Api.GetServiceCategory;
import salon.octadevtn.sa.salon.Models.SalonProfile;
import salon.octadevtn.sa.salon.Models.Service;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Service_Category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Service_Category extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SalonProfile salon;
    int id;
    SwipeRefreshLayout swipeRefreshLayout;
    AdaptorService_Category adapter;
    ArrayList<Service> activityList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Service_Category() {
        // Required empty public constructor
    }

    public static Service_Category newInstance(SalonProfile salon, int id) {
        Service_Category fragment = new Service_Category();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, salon);
        args.putInt(ARG_PARAM2, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            salon = (SalonProfile) getArguments().getSerializable(ARG_PARAM1);
            id = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_service__category, container, false);
        ((TextView) v.findViewById(R.id.title)).setText(salon.getSalon().getUsername());
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_regular);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        activityList = new ArrayList<>();
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        adapter = new AdaptorService_Category(activityList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(true);
                api();
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

                api();
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

    public void api() {

        new GetServiceCategory().category(id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                activityList.clear();
                if (result != null) {
                    swipeRefreshLayout.setRefreshing(false);


                    Service[] ser = (Service[]) result;
                    for (int i = 0; i < ser.length; i++) {
                        activityList.add(ser[i]);
                        adapter.notifyDataSetChanged();

                    }
                } else showError();

            }

            @Override
            public void onFailure(Object result) {
                showError();

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                showError();
            }
        });
    }

    public void showError() {
        if (Service_Category.this.isVisible()) {
            swipeRefreshLayout.setRefreshing(false);

            int gravity = LEFT;
            if (Locale.getDefault().getLanguage() == "ar")
                gravity = RIGHT;
            final Alerter a = Alerter.create(getActivity())
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))
                    .setIcon(getResources().getDrawable(R.drawable.wifisi))
                    .setContentGravity(gravity)

                    .setDuration(2000)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
