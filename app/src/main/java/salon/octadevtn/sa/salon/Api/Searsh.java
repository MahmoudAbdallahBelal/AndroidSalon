package salon.octadevtn.sa.salon.Api;

import android.util.Log;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.search;

/**
 * Created by Octadev on 7/14/2017.
 */

public class Searsh {
    public void Searsh(final String country, final String city, final String genre, final String sort,
                       final String id_user, final String type_user, final String sear
            , final UniversalCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Liste_reservation", response.toString());
                try {
                    callBack.onFinish();
                    //if ()
                    JSONObject responseObj = new JSONObject(response);
                    if (responseObj.getString("error").equals(true))
                        callBack.onFailure(null);
                    else {
                        callBack.onResponse(response);
                    }
                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
                    Toast.makeText(MyApplication.getAppContext(), e.toString() + "", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    callBack.onFailure(null);
                    Toast.makeText(MyApplication.getInstance(), e.toString() + "", Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (country != null)
                    params.put("country", country);
                if (city != null)
                    params.put("city", city);
                if (genre != null)
                    params.put("genre", genre);
                params.put("type_user", type_user);

                params.put("id_user", id_user);
                if (sear != null)
                    params.put("search", sear);
                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                return map;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }
}
