package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.labo.kaji.fragmentanimations.PushPullAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Api.About;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutAs.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutAs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutAs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.back1)
    TextView back;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AboutAs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutAs.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutAs newInstance(String param1, String param2) {
        AboutAs fragment = new AboutAs();
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
        View view = inflater.inflate(R.layout.fragment_about_as, container, false);
        ButterKnife.bind(this, view);
        text.setTypeface(MyApplication.type_jf_light);
        back.setTypeface(MyApplication.type_jf_regular);
        title.setTypeface(MyApplication.type_jf_medium);
        swipeRefreshLayout.setRefreshing(true);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).onBackPressed();
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
                About();
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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return PushPullAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    public void About() {
        new About().About(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    text.setText(Html.fromHtml(String.valueOf(result)));
                    swipeRefreshLayout.setEnabled(false);

                }


            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";


            }

            @Override
            public void OnError(String message) {
            }

            @Override
            public void onFinish() {
            }
        });

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
