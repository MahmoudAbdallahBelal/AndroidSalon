package salon.octadevtn.sa.salon.Utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import salon.octadevtn.sa.salon.Login_signup.LoginActivity;


public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static final String Work = "Work";
    private static final String payment = "payment";
    private static final boolean Hidden_male = false;
    private static final boolean Hidden_Female = false;
    private static final boolean sound_notification = false;
    private static final int lang = 0;
    private static final int Currency = 0;
    private static final String maps_searsh = "10";
    public static Typeface type_jf_regular;
    public static Typeface type_jf_light;
    public static Typeface type_jf_medium;
    private static MyApplication mInstance;
    boolean verif = false;
    private RequestQueue mRequestQueue;
    private MyPreferenceManager pref;
    private ImageLoader mImageLoader;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;

        if (Integer.parseInt(this.getPrefManager().getLang()) == 0) {
            type_jf_regular = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Regular.ttf");
            type_jf_medium = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Medium.ttf");
            type_jf_light = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins/Poppins-Light.ttf");
        } else {
            type_jf_regular = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_regular.ttf");
            type_jf_medium = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_medium.ttf");
            type_jf_light = Typeface.createFromAsset(this.getAssets(), "fonts/Jf_flat/JF_Flat_light.ttf");

        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void logout() {
        clearApplicationData();
        Intent intent = new Intent(this, LoginActivity.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }
}