package salon.octadevtn.sa.salon.Api;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/6/2017.
 */

public class Sign_up_salon {
    private static final int MY_SOCKET_TIMEOUT_MS = 30000;

    public void salon(final String email, final String password, final String salon_name, final String username, final String salon_type,
                      final String since_from, final String phone_number, final String city, final String contry, final String gendre
            , final String token, final UniversalCallBack callBack) {
        String url = UrlStatic.Sign_up_salon;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LoginSalon", response.toString());

                try {
                    JSONObject responseObj = new JSONObject(response);
                    if (responseObj.getString("error").equals("true")) {
                        Toasty.info(MyApplication.getInstance(), responseObj.getString("message"), Toast.LENGTH_SHORT, true).show();
                        callBack.onFailure(null);
                    } else {
                        Gson gson = new Gson();
                        Profile profile = new Profile();
                        profile.setType(responseObj.getString("type"));
                        Salon responseObject = gson.fromJson(responseObj.getJSONObject("user").toString(), Salon.class);
                        profile.setSalon(responseObject);
                        profile.setUserId(responseObject.getId());
                        String work_s = new Gson().toJson(profile.getSalon().getWork());
                        String payment_s = new Gson().toJson(profile.getSalon().getPayment());
                        MyApplication.getInstance().getPrefManager().setWork(work_s);
                        MyApplication.getInstance().getPrefManager().setPayment(payment_s);

                        callBack.onResponse(profile);
                    }
                } catch (JsonSyntaxException e) {
                    Toast.makeText(MyApplication.getInstance(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    callBack.onFailure(null);
                } catch (JSONException e) {
                    callBack.onFailure(null);

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                String message = null;
                Log.d("onErrorResponse", error.toString() + "");
                String json = null;
                Log.d("error.getMessage()", error.getMessage() + "");
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    callBack.OnError(message);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    callBack.OnError(message);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    callBack.OnError(message);
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    callBack.OnError(message);
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    callBack.OnError(message);
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    callBack.OnError(message);
                } else {
                    try {
                        Gson gson = new Gson();
                        ResponseErrors ErrorMsg = gson.fromJson(error.getMessage(), ResponseErrors.class);
                        callBack.onFailure(ErrorMsg);
                    } catch (JsonSyntaxException e) {
                        callBack.onFailure(null);
                    }
                }
            }
        })


        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);
                params.put("salon_name", salon_name);
                params.put("username", username);
                params.put("salon_type", salon_type);
                params.put("since_from", since_from);
                params.put("phone_number", phone_number);
                params.put("city", city);
                params.put("country", contry);
                params.put("gender", gendre);
                params.put("gcm", token);
                return checkParams(params);

            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }
}
