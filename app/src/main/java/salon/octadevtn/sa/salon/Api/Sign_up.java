package salon.octadevtn.sa.salon.Api;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.Models.User;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.Utils.VolleyMultipartRequest;
import salon.octadevtn.sa.salon.Utils.VolleySingleton;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.Updatecustomer;
import static salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service.getFileDataFromDrawable;

/**
 * Created by Marwen octadev on 7/6/2017.
 */

public class Sign_up {
    private static final int MY_SOCKET_TIMEOUT_MS =30000 ;
    public User user;

    public void Costomer(final String name, final String email, final String phone, final String genre, final String username, final String password
            , final String token, final UniversalCallBack callBack) {
        String url = UrlStatic.Sign_up_costumer;
        Log.d("Login", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject responseObj = new JSONObject(response);
                    if (responseObj.getBoolean("error")) {
                        Toasty.info(MyApplication.getInstance(), responseObj.getString("message"), Toast.LENGTH_SHORT, true).show();
                        callBack.onFailure(null);
                    } else {
                        Gson gson = new Gson();
                        Profile profile = new Profile();
                        profile.setType(responseObj.getString("type"));
                        if (responseObj.getString("type").equals("customer")) {
                            User responseObject = gson.fromJson(responseObj.getJSONObject("user").toString(), User.class);
                            profile.setUser(responseObject);
                            profile.setUserId(responseObject.getId());
                            Log.d("profile", profile.getUser().toString());
                        } else {
                            Salon responseObject = gson.fromJson(responseObj.getJSONObject("user").toString(), Salon.class);
                            profile.setSalon(responseObject);
                            profile.setUserId(responseObject.getId());
                            Log.d("profile", profile.getUser().toString());

                        }

                        callBack.onResponse(profile);
                    }
                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
                    Toast.makeText(MyApplication.getInstance(), e.toString()+"", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    callBack.onFailure(null);
                    Toast.makeText(MyApplication.getInstance(), e.toString()+"  ++++", Toast.LENGTH_SHORT).show();

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

                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("genre", genre);
                params.put("username", username);
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
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    public void UpdateCostomer(final Bitmap photo, final Bitmap cover, final String name
            , final String gendre, final String country, final String city, final String email
            , final String phone, final String bio
            , String use, final int delcover, final Context c, final UniversalCallBack callBack) {

        final Gson gson = new Gson();
        user = gson.fromJson(String.valueOf(use), User.class);
        Log.d("biobio", user.getBio());

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Updatecustomer, new Response.Listener<com.android.volley.NetworkResponse>() {
            @Override
            public void onResponse(com.android.volley.NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String error = result.getString("error");

                    if (error.equals("false")) {
                        callBack.onResponse("true");
                        //    Toasty.success(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.succes), Toast.LENGTH_SHORT, true).show();
                        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);

                        try {
                            salon.octadevtn.sa.salon.Models.Profile profile = (salon.octadevtn.sa.salon.Models.Profile) gsonSharedPrefs.getObject(new salon.octadevtn.sa.salon.Models.Profile());
                            Gson gson = new Gson();


                            salon.octadevtn.sa.salon.Models.User responseObject = gson.fromJson(result.getJSONObject("user").toString(), salon.octadevtn.sa.salon.Models.User.class);

                            profile.setUser(responseObject);
                            gsonSharedPrefs.saveObject(profile);
                            ((HomeActivityDrawer) c).settionprofile();

                        } catch (ParsingException e) {
                            e.printStackTrace();
                        }

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
                Toast.makeText(MyApplication.getAppContext(), error.toString() + "", Toast.LENGTH_SHORT).show();
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
                GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                } catch (ParsingException e) {
                    e.printStackTrace();
                }
                params.put("id_user", profile.getUser().getId() + "");
                if (email != null)
                    params.put("email", email);
                if (name != null)
                    params.put("name", name);
                if (gendre != null)
                    params.put("genre", gendre);
                if (phone != null)
                    params.put("phone", phone);
                if (country != null) {
                    params.put("country", country);
                }
                if (city != null)
                    params.put("city", city);
                if (bio != null)
                    params.put("bio", bio);
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    params.put("lan", "ar");
                else
                    params.put("lan", "en");
                params.put("delcover", delcover + "");

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (photo != null) {
                    params.put("image", new DataPart("salon.jpg", getFileDataFromDrawable(MyApplication.getAppContext(), photo), "image/jpeg"));
                }
                if (cover != null) {
                    params.put("cover", new DataPart("salon.jpg", getFileDataFromDrawable(MyApplication.getAppContext(), cover), "image/jpeg"));
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(multipartRequest);
    }

}
