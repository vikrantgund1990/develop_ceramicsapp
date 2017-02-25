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
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import demo.loylty.com.goalsapp.utils.AppConstants;


/**
 * Volley adapter for JSON requests that will be parsed into Java objects by
 */
public class GsonGetRequest<T> extends Request<T> {
	private final Gson gson = new Gson();
	private final Type clazz;
	private final Map<String, String> headers;
	private final Listener<T> listener;
	private ProgressDialog progressDialog;


	/**
	 * Make a POST request and return a parsed object from JSON. Assumes
	 * {@link Method#POST}.
	 *
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 * @param headers
	 *            Map of request headers
	 */
	public GsonGetRequest(String url, Type clazz, JSONObject queryParams, Map<String, String> headers, Listener<T> listener, ErrorListener errorListener, final Activity activity) {
		super(Method.GET, url+params(queryParams), errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
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
		Log.e("KK","req "+url+params(queryParams).toString());
	}

	/**
	 * Like the other, but allows you to specify which {@link Method} you want.
	 *
	 * @param method
	 * @param url
	 * @param clazz
	 * @param headers
	 * @param listener
	 * @param errorListener
	 */
	public GsonGetRequest(int method, String url, Class<T> clazz, JSONObject queryParams, Map<String, String> headers, Listener<T> listener, ErrorListener errorListener, Activity activity) {
		super(method, url+params(queryParams), errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
		progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");
		Log.e("KK","req "+url+params(queryParams).toString());
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headerMap = new HashMap<String , String >();
	    headerMap.put(AppConstants.KEY_ACCESS_TOKEN, AppConstants.ACCESS_TOKEN);
		Log.e("KK","header "+headerMap.toString());
		
		return headerMap != null ? headerMap : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		if(progressDialog!=null)progressDialog.dismiss();

		listener.onResponse(response);


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
	
	
	
	private static String params(JSONObject params)
	{
		String queryParams="";
	if (params.length() != 0) {
		if (!queryParams.endsWith("?"))
			queryParams += "?";

		JSONArray names = params.names();
		for (int i = 0; i < params.length(); i++) {
			try {
				try {
					queryParams += URLEncoder.encode((names.getString(i)), "UTF-8") + "="
							+ URLEncoder.encode(String.valueOf(params.get(names.getString(i))), "UTF-8") + "&";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	return queryParams;
	}
}