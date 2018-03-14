package salon.octadevtn.sa.salon.Reservation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Adaptor.AdapterReservation;
import salon.octadevtn.sa.salon.Models.Reservation.Reservation;
import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeReservationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static FragmentManager manager;
    static RecyclerView recyclerView;
    static LinearLayout nodata;
    static SwipeRefreshLayout swipeRefreshLayout;
    static AdapterReservation adapterReservation;
    static Context context;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListeReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeReservationFragment newInstance(String param1, String param2) {
        ListeReservationFragment fragment = new ListeReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void ListeReservation() {
        nodata.setVisibility(View.GONE);

        new salon.octadevtn.sa.salon.Api.Reservation.ListeReservation().ListeReservation("1"
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            Reservation reservation = (Reservation) result;
                            ArrayList<Task> tasks = new ArrayList<Task>();
                            tasks.addAll(reservation.getTasks());
                            adapterReservation = new AdapterReservation(tasks, recyclerView, context);
                            recyclerView.setAdapter(adapterReservation);
                            adapterReservation.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            if (reservation.getTasks().size() == 0) {
                                nodata.setVisibility(View.VISIBLE);
                            } else nodata.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        swipeRefreshLayout.setRefreshing(false);
                        nodata.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void OnError(String message) {
                        nodata.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);


                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_liste_reservation, container, false);
        ButterKnife.bind(this, view);
        ((TextView) view.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);
        nodata = view.findViewById(R.id.nodata);
        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        back.setTypeface(MyApplication.type_jf_regular);
        title.setTypeface(MyApplication.type_jf_medium);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterReservation.notifyDataSetChanged();
                ListeReservation();
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

                ListeReservation();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(999);
        GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gl);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setDrawingCacheEnabled(true);


        return view;
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
