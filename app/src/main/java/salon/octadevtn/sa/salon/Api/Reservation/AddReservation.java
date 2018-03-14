package salon.octadevtn.sa.salon.Api.Reservation;

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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import salon.octadevtn.sa.salon.Models.Error;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.Add_reservation;
import static salon.octadevtn.sa.salon.Utils.UrlStatic.UpdateReserve;

/**
 * Created by Octadev on 7/15/2017.
 */

public class AddReservation {
    public void AddReservation(final String id_user, final String id_salon, final String id_promotion, final String start_date, final String date, final String time,
                               final UniversalCallBack callBack) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Add_reservation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Reservationresponse", response.toString());
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    Error responseObject = gson.fromJson(response.toString(), Error.class);
                    try {
                        callBack.onResponse(responseObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
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


                params.put("id_customer", id_user + "");
                params.put("id_salon", id_salon + "");
                params.put("id_promotion", id_promotion + "");
                params.put("date", date + "");
                params.put("time", time + "");
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

    public void UpdateReservation(final String id_user, final String id_salon, final String id_promotion, final String start_date, final String date, final String time,final String reser,
                               final UniversalCallBack callBack) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateReserve, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Reservationresponse", response.toString());
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    Error responseObject = gson.fromJson(response.toString(), Error.class);
                    try {
                        callBack.onResponse(responseObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JsonSyntaxException e) {
                    callBack.onFailure(null);
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


                params.put("reservation_id", reser + "");
                params.put("id_customer", id_user + "");
                params.put("id_salon", id_salon + "");
                params.put("id_promotion", id_promotion + "");
                params.put("date", date + "");
                params.put("time", time + "");
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
