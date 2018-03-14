package salon.octadevtn.sa.salon.Api;

import android.util.Log;

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
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.ServiceDetail;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/6/2017.
 */

public class Service {


    public void getService(int id_service, final UniversalCallBack callBack) {
        String url = "";
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (profile.getType().equals("salon")) {
            url = UrlStatic.getService + "/" + profile.getSalon().getId() + "/salon/" + id_service;
        } else {
            url = UrlStatic.getService + "/" + profile.getUser().getId() + "/customer/" + id_service;

        }
        Log.d("Login", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Service_Tesxt", response.toString());
                try {
                    callBack.onFinish();
                    //if ()

                    Gson gson = new Gson();
                    ServiceDetail responseObject = gson.fromJson(response, ServiceDetail.class);
                    if (responseObject.getError().equals("true"))
                        callBack.onResponse(null);
                    else
                        callBack.onResponse(responseObject);

                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Service_Tesxt", error.toString());

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
                4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


}
