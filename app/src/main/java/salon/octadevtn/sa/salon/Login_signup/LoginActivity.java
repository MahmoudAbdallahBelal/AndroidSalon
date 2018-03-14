package salon.octadevtn.sa.salon.Login_signup;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Api.LoginAPI;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.Circlebutton.CircularProgressButton;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Salon out put";
    CallbackManager mCallbackManager;
    TwitterLoginButton mLoginButton;
    String token;

    @BindView(R.id.login)
    CircularProgressButton login;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.forget_pssword1)
    TextView forget_pssword1;
    @BindView(R.id.sign_up1)
    TextView sign_up1;
    @BindView(R.id.forget_pssword)
    LinearLayout forget_pssword;
    @BindView(R.id.sign_up)
    LinearLayout sign_up;
    @BindView(R.id.or)
    TextView or;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        token = FirebaseInstanceId.getInstance().getToken();
        setContentView(R.layout.activity_login);
        final RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }
        ButterKnife.setDebug(true);
        ButterKnife.bind(LoginActivity.this);
        username.setTypeface(MyApplication.type_jf_regular);
        forget_pssword1.setTypeface(MyApplication.type_jf_regular);
        sign_up1.setTypeface(MyApplication.type_jf_regular);
        or.setTypeface(MyApplication.type_jf_regular);
        login.setTypeface(MyApplication.type_jf_regular);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getProgress() == -1) {
                    login.setProgress(0);
                }
                if (username.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(username);

                } else {
                    if (password.getText().toString().equals("")) {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .repeat(1)
                                .playOn(password);
                    } else {


                        login.setIndeterminateProgressMode(true); // turn on indeterminate progress
                        login.setProgress(50);

                        new LoginAPI().Login(username.getText().toString(), password.getText().toString(), token
                                , new UniversalCallBack() {
                                    @Override
                                    public void onResponse(Object result) {
                                        if (result != null) {
                                            Profile user = (Profile) result;
                                            GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(LoginActivity.this, Static.shared_name);
                                            gsonSharedPrefs.saveObject(user);

                                            if (user.getType().equals("salon")) {
                                              /*
                                                if (user.getSalon().getState().equals("0")) {
                                                    final Dialog dialog = new Dialog(LoginActivity.this);
                                                    dialog.setContentView(R.layout.alert);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                    dialog.show();
                                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                                    lp.copyFrom(dialog.getWindow().getAttributes());
                                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                                    dialog.getWindow().setAttributes(lp);
                                                    dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                           finish;
                                                        }
                                                    });
                                                } else {
                                                */
                                                    String work_s = new Gson().toJson(user.getSalon().getWork());
                                                    String pay_s = new Gson().toJson(user.getSalon().getPayment());
                                                    MyApplication.getInstance().getPrefManager().setWork(work_s);
                                                    MyApplication.getInstance().getPrefManager().setPayment(pay_s);
                                                    login.setProgress(100);
                                                    startActivity(new Intent(LoginActivity.this, HomeActivityDrawer.class));
                                                    finish();
//                                                }
                                            } else if (!user.getType().equals("salon")) {

                                                login.setProgress(100);
                                                startActivity(new Intent(LoginActivity.this, HomeActivityDrawer.class));
                                                finish();
                                            }

                                        } else
                                            login.setProgress(-1);


                                    }

                                    @Override
                                    public void onFailure(Object result) {
                                        ResponseErrors responseError = (ResponseErrors) result;
                                        String Error = "Failure";
                                        login.setProgress(-1);


                                    }

                                    @Override
                                    public void OnError(String message) {
                                        login.setProgress(-1);


                                    }

                                    @Override
                                    public void onFinish() {

                                    }
                                });


                    }
                }
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_fb);
        loginButton.setBackgroundResource(R.drawable.background_bouton_facebook);
        loginButton.setTypeface(MyApplication.type_jf_regular);
        // loginButton.setCompoundDrawablesWithIntrinsicBounds(null,  null,getResources().getDrawable(R.drawable.facebook_letter), null);
        //  loginButton.setCompoundDrawablePadding(20);
        loginButton.setPadding(23, 23, 23, 23);
        loginButton.setText(getResources().getString(R.string.log_in_facebook));
        loginButton.setTextSize(12);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
        mLoginButton = (TwitterLoginButton) findViewById(R.id.button_twitter_login);
        // mLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mLoginButton.setBackgroundResource(R.drawable.background_bouton_twitter);
        mLoginButton.setCompoundDrawablePadding(0);
        mLoginButton.setPadding(20, 20, 20, 20);
        mLoginButton.setTypeface(MyApplication.type_jf_regular);
        mLoginButton.setText(getResources().getString(R.string.log_in_twitter));
        mLoginButton.setTextSize(12);
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
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Sign_up.class);
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);

            }
        });
        forget_pssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);

      /*
                 AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
        finish();
    }

    private void hideNavBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
