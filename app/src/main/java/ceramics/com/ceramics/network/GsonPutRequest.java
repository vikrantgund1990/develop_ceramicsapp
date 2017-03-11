package ceramics.com.ceramics.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import ceramics.com.ceramics.utils.AppConstants;


/**
 * Created by adhirajs on 4/22/16.
 */
public class GsonPutRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Type clazz;
    private final JSONObject params;

    private final Response.Listener<T> listener;
    private ProgressDialog progressDialog;
    private Activity activity;

    /**
     * Make a POST request and return a parsed object from JSON. Assumes
     * {@link Method#POST}.
     *
     * @param url
     *            URL of the request to make
     * @param clazz
     *            Relevant class object, for Gson's reflection

     *            Map of request headers
     */
    public GsonPutRequest(String url, Type clazz, JSONObject params, Response.Listener<T> listener, Response.ErrorListener errorListener, final Activity activity) {
        super(Method.PUT, url, errorListener);
        this.clazz = clazz;
        this.params=params;
        this.listener = listener;
        this.activity=activity;
        progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                try {

                    activity.finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Log.e("KK", url + "\n" + params.toString());
    }

    /**
     * Like the other, but allows you to specify which {@link Method} you want.
     *
     * @param method
     * @param url
     * @param clazz

     * @param listener
     * @param errorListener
     */
    public GsonPutRequest(int method, String url, Class<T> clazz, JSONObject params, Response.Listener<T> listener, Response.ErrorListener errorListener, Activity activity) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.params=params;
        this.activity=activity;
        this.listener = listener;

        progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");
        Log.e("KK",url+"\n"+params.toString());
    }




    /**
     * Make a POST request and return a parsed object from JSON. Assumes
     * {@link Method#POST}.
     *
     * @param url
     *            URL of the request to make
     * @param clazz
     *            Relevant class object, for Gson's reflection

     *            Map of request headers
     */
    public GsonPutRequest(String url, Type clazz, JSONObject params, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, url, errorListener);
        this.clazz = clazz;
        this.params=params;
        this.listener = listener;


    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headerMap = new HashMap<String , String >();
        Log.e("KK","header "+headerMap.toString());

        return headerMap != null ? headerMap : super.getHeaders();
    }
    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return params.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
    @Override
    protected void deliverResponse(T response) {

        listener.onResponse(response);
        if(progressDialog!=null)progressDialog.dismiss();


    }
    @Override
    public void deliverError(VolleyError error) {
        // TODO Auto-generated method stub
        if(progressDialog!=null)progressDialog.dismiss();
        super.deliverError(error);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if(progressDialog!=null)progressDialog.dismiss();

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.e("KK",json);
            return (Response<T>) Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }

    }
}