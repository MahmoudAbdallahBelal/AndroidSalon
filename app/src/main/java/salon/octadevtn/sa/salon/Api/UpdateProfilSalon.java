package salon.octadevtn.sa.salon.Api;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.VolleyMultipartRequest;
import salon.octadevtn.sa.salon.Utils.VolleySingleton;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.UpdateSalon;
import static salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service.getFileDataFromDrawable;

/**
 * Created by Marwen octadev on 7/6/2017.
 */

public class UpdateProfilSalon {
    public void UpdateProfilSalon(final String email, final String branches, final String salon_name, final String username, final String salon_type,
                                  final String since_from, final String phone_number, final String city, final String contry, final String work,
                                  final String payment, final String gender, final String latitude, final String longitude, final String album
            , final Bitmap image, final String work_type, final String adress, final UniversalCallBack callBack) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, UpdateSalon, new Response.Listener<com.android.volley.NetworkResponse>() {
            @Override
            public void onResponse(com.android.volley.NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String error = result.getString("error");
                    if (error.equals("false")) {
                        Toasty.success(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.succes), Toast.LENGTH_SHORT, true).show();
                        callBack.onResponse("true");

                    } else {
                        callBack.onResponse(null);
                        Toasty.info(MyApplication.getInstance(), result.getString("message"), Toast.LENGTH_SHORT, true).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    callBack.onResponse(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                com.android.volley.NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("username", username + "");
                params.put("salon_name", salon_name + "");
                // params.put("salon_type", salon_type+"");
                if (Static.sincefrom == true)
                    params.put("since_from", since_from + "");
                params.put("phone_number", phone_number + "");
                if (contry != null)
                    params.put("country", contry + "");
                if (city != null)
                    params.put("city", city + "");
                if (work_type != null)
                    params.put("work_type", work_type + "");
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("gender", gender + "");
                Log.d("paymentP", payment);
                params.put("payment", payment);
                params.put("work", work);
                params.put("branches", branches + "");
                params.put("adress", adress + "");
                GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                } catch (ParsingException e) {
                    e.printStackTrace();
                }
                params.put("id_salon", profile.getSalon().getId() + "");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (image != null)
                    params.put("image", new DataPart("salon.jpg", getFileDataFromDrawable(MyApplication.getAppContext(), image), "image/jpeg"));

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(multipartRequest);
    }
}
