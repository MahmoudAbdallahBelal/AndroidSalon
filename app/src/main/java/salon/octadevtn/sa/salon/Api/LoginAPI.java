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
import salon.octadevtn.sa.salon.Models.User;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by hazemz.lababidi on 6/11/17.
 */

public class LoginAPI {

    public void Login(final String email, final String password
            , final String token, final UniversalCallBack callBack) {
        String url = UrlStatic.login;
        Log.d("Login", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login", response.toString());
                try {
                    callBack.onFinish();
                    //if ()
                    JSONObject responseObj = new JSONObject(response);
                    if (responseObj.getString("error").equals("true")) {
                        // callBack.onFailure(null);
                        callBack.onResponse(null);
                        Toasty.info(MyApplication.getInstance(), responseObj.getString("message"), Toast.LENGTH_SHORT, true).show();
                    } else {
                        Gson gson = new Gson();
                        Profile profile = new Profile();
                        profile.setType(responseObj.getString("type"));
                        if (responseObj.getString("type").equals("customer")) {
                            User responseObject = gson.fromJson(responseObj.getJSONObject("user").toString(), User.class);
                            profile.setUser(responseObject);
                            profile.setUserId(responseObject.getId());

                        } else {

                            Salon responseObject = gson.fromJson(responseObj.getJSONObject("user").toString(), Salon.class);
                            profile.setSalon(responseObject);
                            profile.setUserId(responseObject.getId());

                            String work_s = new Gson().toJson(profile.getSalon().getWork());
                            String payment_s = new Gson().toJson(profile.getSalon().getPayment());
                            MyApplication.getInstance().getPrefManager().setWork(work_s);
                            MyApplication.getInstance().getPrefManager().setPayment(payment_s);


                        }

                        callBack.onResponse(profile);
                    }
                } catch (JsonSyntaxException e) {
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
                callBack.onFailure(null);

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
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    params.put("lan", "ar");
                else
                    params.put("lan", "en");
                params.put("email", email);
                params.put("password", password);
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
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

}
