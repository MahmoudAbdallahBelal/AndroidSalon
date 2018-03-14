package salon.octadevtn.sa.salon.Utils;


import org.json.JSONException;

public interface UniversalCallBack {

    void onResponse(Object result) throws JSONException;

    void onFailure(Object result);

    void onFinish();

    void OnError(String message);

}
