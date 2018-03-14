package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Adaptor.AdaptorServices_Liste;
import salon.octadevtn.sa.salon.Models.SalonProfile;
import salon.octadevtn.sa.salon.Models.ServicesCategory;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link listeServiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link listeServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listeServiceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SalonProfile salonProfile;
    // TODO: Rename and change types of parameters
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public listeServiceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static listeServiceFragment newInstance(salon.octadevtn.sa.salon.Models.SalonProfile salonProfile) {
        listeServiceFragment fragment = new listeServiceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, salonProfile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            salonProfile = (SalonProfile) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liste, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        ((TextView) v.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

        ArrayList<ServicesCategory> activityList = new ArrayList<>();
        activityList.addAll(salonProfile.getServicesCategory());
        if (activityList.size() == 0) {
            v.findViewById(R.id.nodata).setVisibility(View.VISIBLE);
        }
        AdaptorServices_Liste adapter = new AdaptorServices_Liste(getActivity(), activityList, salonProfile);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        runLayoutAnimation(recyclerView);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
