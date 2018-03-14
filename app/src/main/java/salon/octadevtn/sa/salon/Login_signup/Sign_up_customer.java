package salon.octadevtn.sa.salon.Login_signup;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Api.VerifUserName;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.Circlebutton.CircularProgressButton;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.fragment.FirstListeFlow;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sign_up_customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_up_customer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Salon out put";
    String token;
    TwitterLoginButton mLoginButton;
    String genre;
    @BindView(R.id.genre_user)
    EditText genre_user;
    @BindView(R.id.login_user)
    EditText login_user;
    @BindView(R.id.usernameverif)
    ImageView usernameverif;
    @BindView(R.id.usernameprogress)
    ProgressBar usernameprogress;
    @BindView(R.id.name_user)
    EditText name_user;
    @BindView(R.id.phone_user)
    EditText phone_user;
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.condition)
    CheckBox condition;
    @BindView(R.id.login)
    CircularProgressButton login;
    @BindView(R.id.prograess)
    AVLoadingIndicatorView progress;
    TextView condi;

    Boolean pass = true;
    boolean username_verifed = false;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Sign_up_customer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sign_up_customer.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_up_customer newInstance(String param1, String param2) {
        Sign_up_customer fragment = new Sign_up_customer();
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
        view = inflater.inflate(R.layout.fragment_sign_up_customer, container, false);

       condi=view.findViewById(R.id.condi);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 500) { // if more than 100 pixels, its probably a keyboard...

                }
            }
        });

        token = FirebaseInstanceId.getInstance().getToken();
        String msg = getString(R.string.msg_token_fmt, token);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        email_user.setTypeface(MyApplication.type_jf_regular);
        genre_user.setTypeface(MyApplication.type_jf_regular);
        login_user.setTypeface(MyApplication.type_jf_regular);
        phone_user.setTypeface(MyApplication.type_jf_regular);
        name_user.setTypeface(MyApplication.type_jf_regular);
        password.setTypeface(MyApplication.type_jf_regular);
        // loginButton.setCompoundDrawablesWithIntrinsicBounds(null,  null,getResources().getDrawable(R.drawable.facebook_letter), null);
        //  loginButton.setCompoundDrawablePadding(20);

        login_user.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                new VerifUserName().Costumer(login_user.getText().toString()
                        , new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                Boolean verif = (Boolean) result;
                                if (verif) {
                                    verifusername(100);
                                    username_verifed = true;
                                } else {
                                    verifusername(-1);
                                    username_verifed = false;
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

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifusername(50);

            }
        });
        genre_user.setFocusable(false);
        genre_user.setClickable(true);

        genre_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(genre_user);
            }
        });
        mLoginButton = (TwitterLoginButton) view.findViewById(R.id.button_twitter_login);
        mLoginButton.setTypeface(MyApplication.type_jf_regular);

        // mLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mLoginButton.setBackgroundResource(R.drawable.background_bouton_twitter);
        mLoginButton.setCompoundDrawablePadding(0);
        mLoginButton.setPadding(25, 25, 25, 25);
        mLoginButton.setText(getResources().getString(R.string.log_in_twitter));
        mLoginButton.setTextSize(12);
        mLoginButton.setTypeface(MyApplication.type_jf_regular);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = true;

                if (login.getProgress() == -1) {
                    login.setProgress(0);
                }
                if (name_user.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(name_user);
                    pass = false;
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_name), Toast.LENGTH_SHORT).show();

                } else if (!username_verifed) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(login_user);
                    pass = false;

                } else if (genre_user.getText().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(genre_user);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_genre), Toast.LENGTH_SHORT).show();

                } else if (email_user.getText().equals("") ||!isEmailValid(email_user.getText().toString())) {

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(email_user);
                    pass = false;
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_mail), Toast.LENGTH_SHORT).show();

                } else if (password.getText().equals("") || password.getText().length() < 6) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(password);
                    pass = false;
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_password), Toast.LENGTH_SHORT).show();

                } else if (!condition.isChecked()) {
                    pass = false;
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(condition);
                }

                if (pass) {
                    login.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    new salon.octadevtn.sa.salon.Api.Sign_up().Costomer(name_user.getText().toString(), email_user.getText().toString(), phone_user.getText().toString(), genre, login_user.getText().toString(), password.getText().toString()
                            , token, new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    Toast.makeText(getActivity(),result.toString(), Toast.LENGTH_SHORT).show();
                                    if (result != null) {
                                        Profile user = (Profile) result;
                                        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(getActivity(), Static.shared_name);
                                        gsonSharedPrefs.saveObject(user);
                                        login.setProgress(100);
                                        startActivity(new Intent(getActivity(), FirstListeFlow.class));
                                        getActivity().finish();
                                    } else
                                        // login.setProgress(-1);
                                        login.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Object result) {
                                    ResponseErrors responseError = (ResponseErrors) result;
                                    String Error = "Failure";
                                    //login.setProgress(-1);
                                    login.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);

                                    Toast.makeText(getActivity(),result.toString(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void OnError(String message) {
                                    // login.setProgress(-1);

                                    login.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),message.toString(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFinish() {


                                }
                            });


                }
            }
        });
        condi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConditionPolicy.class));

//                getActivity().overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_left);

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);

      /*  AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
*/

    }

    public void showPopupMenu(View view) {
       /* PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.gendre, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.man:
                        genre_user.setText(item.getTitle());
                        genre = "men";
                        return true;
                    case R.id.women:
                        genre_user.setText(item.getTitle());
                        genre = "women";
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
*/
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.gendre);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre_user.setText(((TextView) dialog.findViewById(R.id.man)).getText().toString());
                genre = "men";
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre_user.setText(((TextView) dialog.findViewById(R.id.women)).getText().toString());
                genre = "women";
                dialog.dismiss();
            }
        });


    }

    public void verifusername(int key) {
        switch (key) {
            case -1:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.error_verif));
                break;
            case 50:
                usernameprogress.setVisibility(View.VISIBLE);
                usernameverif.setVisibility(View.GONE);
                break;
            case 100:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.success_verif));
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
