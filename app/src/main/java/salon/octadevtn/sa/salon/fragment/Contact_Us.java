package salon.octadevtn.sa.salon.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.labo.kaji.fragmentanimations.PushPullAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cketti.mailto.EmailIntentBuilder;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contact_Us.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contact_Us#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_Us extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.back)
    TextView back;
//    @BindView(R.id.t1)
//    TextView t1;
//    @BindView(R.id.t2)
//    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.reason)
    EditText reason;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.subject)
    EditText subject;
    @BindView(R.id.send)
    Button send;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Contact_Us() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact_Us.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact_Us newInstance(String param1, String param2) {
        Contact_Us fragment = new Contact_Us();
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
        View view = inflater.inflate(R.layout.fragment_contact__us, container, false);
        ButterKnife.bind(this, view);
        back.setTypeface(MyApplication.type_jf_regular);
        title.setTypeface(MyApplication.type_jf_regular);
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).onBackPressed();
            }
        });
//        t1.setTypeface(MyApplication.type_jf_medium);
//        t2.setTypeface(MyApplication.type_jf_medium);
        t3.setTypeface(MyApplication.type_jf_medium);
        reason.setTypeface(MyApplication.type_jf_light);
        email.setTypeface(MyApplication.type_jf_light);
        subject.setTypeface(MyApplication.type_jf_light);
        send.setTypeface(MyApplication.type_jf_regular);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"saloncom@salon.com.sa"});
//                i.putExtra(Intent.EXTRA_SUBJECT, reason.getText().toString());
//                i.putExtra(Intent.EXTRA_TEXT, subject.getText().toString());
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                }

                boolean success = EmailIntentBuilder.from(getActivity())
                        .to("saloncom@salon.com.sa")
                        .subject(subject.getText().toString())
                        .body(reason.getText().toString())
                        .start();



            }
        });
        return view;
    }

    // TODO click on Gmail Text

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
