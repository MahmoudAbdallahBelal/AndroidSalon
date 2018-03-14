package salon.octadevtn.sa.salon;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Adaptor.AdaptorFlow;
import salon.octadevtn.sa.salon.Models.Follow;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Searsh_profil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searsh_profil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Searsh_profil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh_profil.
     */
    // TODO: Rename and change types and number of parameters
    public static Searsh_profil newInstance(String param1, String param2) {
        Searsh_profil fragment = new Searsh_profil();
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
        View view = inflater.inflate(R.layout.fragment_searsh_profil, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ArrayList<Follow> activityList = new ArrayList<>();


        AdaptorFlow adapter = new AdaptorFlow(activityList, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < Searsh_request.searsh.getSalons().size(); i++) {
            Follow follow = new Follow();
            follow.setGenre(Searsh_request.searsh.getSalons().get(i).getGender());
            follow.setIdUser(Searsh_request.searsh.getSalons().get(i).getId());
            follow.setImage(Searsh_request.searsh.getSalons().get(i).getPhoto());
            follow.setIsFolow(Searsh_request.searsh.getSalons().get(i).getIsFlow());
            follow.setUsername(Searsh_request.searsh.getSalons().get(i).getSalonName());

            follow.setType("salon");
            activityList.add(follow);
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < Searsh_request.searsh.getUsers().size(); i++) {
            Follow follow = new Follow();
            follow.setGenre(Searsh_request.searsh.getUsers().get(i).getGenre());
            follow.setIdUser(Searsh_request.searsh.getUsers().get(i).getId());
            follow.setImage(Searsh_request.searsh.getUsers().get(i).getPhoto());
            follow.setIsFolow(Searsh_request.searsh.getUsers().get(i).getIsFlow());
            follow.setType("customer");
            follow.setUsername(Searsh_request.searsh.getUsers().get(i).getUsername());

            activityList.add(follow);
            adapter.notifyDataSetChanged();
        }
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
