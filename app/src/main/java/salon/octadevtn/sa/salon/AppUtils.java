package salon.octadevtn.sa.salon;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/**
 * Created by Octadev on 7/24/2017.
 */

class AppUtils {
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.LOCATION_MODE);

            } catch (android.provider.Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != android.provider.Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public class LocationConstants {
        public static final int SUCCESS_RESULT = 0;

        public static final int FAILURE_RESULT = 1;

        public static final String PACKAGE_NAME = "tn.sonline.marwenoctadev.sonline.maplocation";

        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

        public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

        public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
        public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
        public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";


    }

}
