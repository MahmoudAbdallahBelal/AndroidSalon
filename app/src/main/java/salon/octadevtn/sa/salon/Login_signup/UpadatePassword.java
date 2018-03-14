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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

public class UpadatePassword extends AppCompatActivity {
    @BindView(R.id.old)
    EditText old;
    @BindView(R.id.new0)
    EditText new0;
    @BindView(R.id.new1)
    EditText new1;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.send)
    Button send;
    String email;
    @BindView(R.id.back)
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        ButterKnife.bind(UpadatePassword.this);
        email = getIntent().getExtras().getString("email");
        old.setTypeface(MyApplication.type_jf_regular);
        new0.setTypeface(MyApplication.type_jf_regular);
        new1.setTypeface(MyApplication.type_jf_regular);

        cancel.setTypeface(MyApplication.type_jf_regular);
        send.setTypeface(MyApplication.type_jf_regular);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpadatePassword.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpadatePassword.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new0.getText().toString().length() < 6)
                    Toasty.warning(UpadatePassword.this, getResources().getString(R.string.w_password)
                            , Toast.LENGTH_SHORT).show();
                else if (new0.getText().toString().equals(new1.getText().toString()))
                    UpdatePassword(old.getText().toString(), new0.getText().toString());
                else
                    Toasty.warning(UpadatePassword.this, getResources().getString(R.string.w_password1)
                            , Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        UpadatePassword.this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);
        finish();
        super.onBackPressed();

    }


    private void UpdatePassword(String old, String new0) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(UpadatePassword.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.color2));
        pDialog.setTitleText(getResources().getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();

        new salon.octadevtn.sa.salon.Api.ForgetPassword().Forget_Password_reset(email, old, new0, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    pDialog.dismiss();
                    new SweetAlertDialog(UpadatePassword.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(getResources().getString(R.string.update_password))
                            .setContentText(getResources().getString(R.string.update_password_do))
                            .setConfirmText(getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Intent intent = new Intent(UpadatePassword.this, LoginActivity.class);
                                    finish();
                                    startActivity(intent);
                                    UpadatePassword.this.overridePendingTransition(R.anim.slide_in_left,
                                            R.anim.slide_out_left);

                                }
                            })
                            .show();


                } else {
                    pDialog.dismiss();

                    new SweetAlertDialog(UpadatePassword.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getResources().getString(R.string.doyousure))
                            .setContentText(getResources().getString(R.string.tokenavailable))
                            .setConfirmText(getResources().getString(R.string.ok))
                            .show();
                }
            }

            @Override
            public void onFailure(Object result) {

            }

            @Override
            public void OnError(String message) {
            }

            @Override
            public void onFinish() {
            }
        });

    }


}
