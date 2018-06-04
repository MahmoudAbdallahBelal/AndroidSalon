package salon.octadevtn.sa.salon.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseDeviceToken extends FirebaseInstanceIdService {

    private static final String TAG = "FireBaseDeviceToken";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token:" + refreshedToken);
        storeToken(refreshedToken);
    }



    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
        Log.d("token" , token);
    }



}
