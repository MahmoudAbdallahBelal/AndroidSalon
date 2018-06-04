package salon.octadevtn.sa.salon.Login_signup;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import salon.octadevtn.sa.salon.R;


public class Sign_up extends AppCompatActivity {

    public static FragmentManager manager;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private int[] tabIcons = {
            R.drawable.salon
            , R.drawable.customer};

    public static void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        manager = getSupportFragmentManager();
        ((ImageView) findViewById(R.id.customer)).setImageDrawable(getResources().getDrawable(R.drawable.customer_selected));
        ((ImageView) findViewById(R.id.salon)).setImageDrawable(getResources().getDrawable(R.drawable.salon_deselected));
        setFragment(new Sign_up_customer());
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign_up.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
                finish();
            }
        });
        findViewById(R.id.custemer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) findViewById(R.id.customer)).setImageDrawable(getResources().getDrawable(R.drawable.customer_selected));
                ((ImageView) findViewById(R.id.salon)).setImageDrawable(getResources().getDrawable(R.drawable.salon_deselected));
                setFragment(new Sign_up_customer());
            }
        });
        findViewById(R.id.salon1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView) findViewById(R.id.customer)).setImageDrawable(getResources().getDrawable(R.drawable.customer_deselected));
                ((ImageView) findViewById(R.id.salon)).setImageDrawable(getResources().getDrawable(R.drawable.salon_selected));
                setFragment(new Sign_up_salon());

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Sign_up.this.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);
        super.onBackPressed();

    }


}