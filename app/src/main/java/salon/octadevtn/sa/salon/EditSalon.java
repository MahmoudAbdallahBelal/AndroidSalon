package salon.octadevtn.sa.salon;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.fragment.profileUpdate.ListePrice;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Liste_Promotion;
import salon.octadevtn.sa.salon.fragment.profileUpdate.MyServiceContainer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditSalon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSalon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.profile_info)
    LinearLayout profile_info;
    @BindView(R.id.service)
    LinearLayout service;
    @BindView(R.id.price)
    LinearLayout price;
    @BindView(R.id.promotion)
    LinearLayout promotion;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditSalon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditSalon.
     */
    // TODO: Rename and change types and number of parameters
    public static EditSalon newInstance(String param1, String param2) {
        EditSalon fragment = new EditSalon();
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
        View view = inflater.inflate(R.layout.fragment_edit_salon, container, false);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        title.setTypeface(MyApplication.type_jf_medium);
        back.setTypeface(MyApplication.type_jf_medium);
        t1.setTypeface(MyApplication.type_jf_medium);
        t2.setTypeface(MyApplication.type_jf_medium);
        t3.setTypeface(MyApplication.type_jf_medium);
        t4.setTypeface(MyApplication.type_jf_medium);
        view.findViewById(R.id.profile_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(new EditProfil());
            }
        });
        view.findViewById(R.id.service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(new MyServiceContainer());
            }
        });
        view.findViewById(R.id.promotion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(new Liste_Promotion());
            }
        });
        view.findViewById(R.id.price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(new ListePrice());
            }
        });


        return view;
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
