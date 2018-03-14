package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Adaptor.AdaptorFlow;
import salon.octadevtn.sa.salon.Models.Follow;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListeFlow.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeFlow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeFlow extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String type;
    private String FoF;
    private int id;
    private OnFragmentInteractionListener mListener;

    public ListeFlow() {
        // Required empty public constructor
    }


    public static ListeFlow newInstance(int id, String type, String FoF) {
        ListeFlow fragment = new ListeFlow();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, type);
        args.putString(ARG_PARAM3, FoF);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            type = getArguments().getString(ARG_PARAM2);
            FoF = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liste_flow, container, false);

        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        final ArrayList<Follow> activityList = new ArrayList<>();


        final AdaptorFlow adapter = new AdaptorFlow(activityList, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        if (FoF.equals("FL")) {

            ((TextView) v.findViewById(R.id.title)).setText(getResources().getString(R.string.following));
            ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
            new salon.octadevtn.sa.salon.Api.Follow().MyFolow(id, type
                    , new UniversalCallBack() {

                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                Follow[] liste = (Follow[]) result;
                                for (int i = 0; i < liste.length; i++) {
                                    activityList.add(liste[i]);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Object result) {

                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void OnError(String message) {

                        }
                    });
        } else {
            ((TextView) v.findViewById(R.id.title)).setText(getResources().getString(R.string.followers));
            ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
            new salon.octadevtn.sa.salon.Api.Follow().Followers(id, type
                    , new UniversalCallBack() {

                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                Follow[] liste = (Follow[]) result;
                                for (int i = 0; i < liste.length; i++) {
                                    activityList.add(liste[i]);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Object result) {

                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void OnError(String message) {

                        }
                    });
        }
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
        return MoveAnimation.create(MoveAnimation.UP, enter, 500L);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
