package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import salon.octadevtn.sa.salon.Adaptor.PriceAdaptor;
import salon.octadevtn.sa.salon.Models.Category;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Salon_price_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Salon_price_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Salon_price_list extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    salon.octadevtn.sa.salon.Models.SalonProfile salonProfile;
    boolean verif = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Salon_price_list() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Salon_price_list newInstance(salon.octadevtn.sa.salon.Models.SalonProfile salonProfile) {
        Salon_price_list fragment = new Salon_price_list();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, salonProfile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            salonProfile = (salon.octadevtn.sa.salon.Models.SalonProfile) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_salon_price_list, container, false);
        ((TextView) v.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

        RecyclerView addHeaderRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        addHeaderRecyclerView.setLayoutManager(linearLayoutManager);
        addHeaderRecyclerView.setHasFixedSize(true);
        List<Category> categories = new ArrayList<>();
        categories.addAll(salonProfile.getCategory());
        if (categories.size() == 0) {
            v.findViewById(R.id.nodata).setVisibility(View.VISIBLE);
        }
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getPrice() != null) {
                if (categories.get(i).getPrice().size() != 0) {
                    PriceAdaptor firstSection = new PriceAdaptor(categories.get(i).getName(), categories.get(i).getPrice());
                    sectionAdapter.addSection(firstSection);
                    verif = true;
                }
            }
        }
        if (!verif) {
            v.findViewById(R.id.nodata).setVisibility(View.VISIBLE);


        }

        addHeaderRecyclerView.setAdapter(sectionAdapter);
        runLayoutAnimation(addHeaderRecyclerView);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
