package salon.octadevtn.sa.salon.Api;

import android.util.Log;

import com.android.volley.AuthFailureError;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import salon.octadevtn.sa.salon.Models.Block;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.Liste_block;

/**
 * Created by Octadev on 7/14/2017.
 */

public class ListeBlock {
    public void ListeBlock(final UniversalCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Liste_block, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login", response.toString());
                try {
                    callBack.onFinish();
                    //if ()
                    JSONObject responseObj = new JSONObject(response);

                    if (!responseObj.getString("error").equals("false")) {
                        callBack.onFailure(null);
                    } else {
                        Gson gson = new Gson();
                        Block block = gson.fromJson(String.valueOf(responseObj), Block.class);
                        callBack.onResponse(block);
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
                GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);

                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                } catch (ParsingException e) {
                    e.printStackTrace();
                }

                if (profile.getType().equals("salon")) {
                    params.put("id_user", profile.getSalon().getId() + "");
                    params.put("type_user", "salon");
                } else {
                    params.put("id_user", profile.getUser().getId() + "");
                    params.put("type_user", "customer");
                }
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

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }
}
