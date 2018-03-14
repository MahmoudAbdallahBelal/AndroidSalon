package salon.octadevtn.sa.salon.Api.Reservation;

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
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Reservation.Reservation;
import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.Liste_reservation;

/**
 * Created by Octadev on 7/14/2017.
 */

public class ListeReservation {
    public void ListeReservation(final String id_user
            , final UniversalCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Liste_reservation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Liste_reservation", response.toString());
                try {
                    callBack.onFinish();
                    //if ()
                    JSONObject responseObj = new JSONObject(response);
                    if (responseObj.getBoolean("error"))
                        callBack.onFailure(null);
                    else {
                        Gson gson = new Gson();
                        Reservation reservation = new Reservation();
                        Task[] task = gson.fromJson(responseObj.getJSONArray("tasks") + "", Task[].class);
                        reservation.setTasks(Arrays.asList(task));
                        callBack.onResponse(reservation);
                    }
                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
                    Toast.makeText(MyApplication.getAppContext(), e.toString() + "", Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                GSONSharedPreferences gsonSharedPrefs2 = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs2.getObject(new Profile());

                } catch (ParsingException e) {
                    e.printStackTrace();
                }

                params.put("user_id", String.valueOf(profile.getUser().getId()));
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
