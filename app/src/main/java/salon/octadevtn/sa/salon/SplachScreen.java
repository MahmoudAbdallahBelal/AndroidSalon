package salon.octadevtn.sa.salon;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;

import java.util.Locale;

import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Utils.FontsOverride;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;

public class SplachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 8) {
            if (Locale.getDefault().getLanguage().equals("ar")) {
                SetLanguage(1);
                MyApplication.getInstance().getPrefManager().setLang(1);


            } else {
                SetLanguage(0);
                MyApplication.getInstance().getPrefManager().setLang(0);

            }

        } else {
            SetLanguage(Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()));

        }
        setContentView(R.layout.activity_splach_screen);
        final ImageView imageView = (ImageView) findViewById(R.id.layer);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0); //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();

        Glide.with(this)
                .load(R.drawable.background02)
                .asBitmap()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        // Do bitmap magic here
                        super.setResource(resource);
                        imageView.setImageBitmap(resource);
                    }
                });

        //        setContentView(R.layout.activity_splach_screen);


        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(SplachScreen.this, Static.shared_name);
                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                    if (profile != null) {
                        Intent i = new Intent(SplachScreen.this, HomeActivityDrawer.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplachScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                } catch (ParsingException e) {
                    e.printStackTrace();
                }


            }
        }, 2 * 1000);


    }


    public void SetLanguage(int lan) {
        Static.popupWindow=null;
        if (lan == 0) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            FontsOverride.setDefaultFont(SplachScreen.this, "MONOSPACE", "fonts/Poppins/Poppins-Regular.ttf");
            MyApplication.type_jf_regular = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Regular.ttf");
            MyApplication.type_jf_medium = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Medium.ttf");
            MyApplication.type_jf_light = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Light.ttf");

        }
        if (lan == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            FontsOverride.setDefaultFont(SplachScreen.this, "MONOSPACE", "fonts/Jf_flat/JF_Flat_regular.ttf");
            MyApplication.type_jf_regular = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_regular.ttf");
            MyApplication.type_jf_medium = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_medium.ttf");
            MyApplication.type_jf_light = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_light.ttf");

        }


    }
}
