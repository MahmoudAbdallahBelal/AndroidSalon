package salon.octadevtn.sa.salon.Login_signup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

public class ForgetPassword extends AppCompatActivity {
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.back)
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(ForgetPassword.this);
        email_user.setTypeface(MyApplication.type_jf_regular);
        send.setTypeface(MyApplication.type_jf_regular);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPassword.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
                finish();
            }
        });
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(email_user.getText().toString())) {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(ForgetPassword.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.color2));
                    pDialog.setTitleText(getResources().getString(R.string.loading));
                    pDialog.setCancelable(false);
                    pDialog.show();
                    new salon.octadevtn.sa.salon.Api.ForgetPassword().Forget_Password(email_user.getText().toString(), new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) throws JSONException {
                            if (result == null) {
                                pDialog.dismiss();
                                new SweetAlertDialog(ForgetPassword.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.doyousure))
                                        .setContentText(getResources().getString(R.string.emailavailable))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .show();
                            } else {
                                pDialog.dismiss();
                                Intent intent = new Intent(ForgetPassword.this, UpadatePassword.class);
                                intent.putExtra("email", email_user.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Object result) {
                            //Toast.makeText(ForgetPassword.this, "Failure\n"+result.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish() {
                           // Toast.makeText(ForgetPassword.this, "Finish\n", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void OnError(String message) {
                          //Toast.makeText(ForgetPassword.this, "Error\n"+message.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(email_user);
                    Toasty.warning(ForgetPassword.this, getResources().getString(R.string.w_mail), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onBackPressed() {
        finish();
        ForgetPassword.this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);
        finish();
        super.onBackPressed();

    }
}
