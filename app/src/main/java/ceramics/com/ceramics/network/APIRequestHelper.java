package ceramics.com.ceramics.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;

import java.lang.reflect.Type;

import ceramics.com.ceramics.utils.CeramicsApplication;


/**
 * @author siddhantp
 * 
 */
public class APIRequestHelper {

	public static final String BASE_URL = "http://qa.ceramicskart.com/api";

	public static String requestType;
	public static Type clazz;
	public static JSONObject queryParams;
	public static Listener<?> listener;
	public static ErrorListener errorListener;
	public static Activity activity;
	public static int RequestType;

	private static <T> void userGetRequest(String requestType, Type clazz,
										   JSONObject queryParams, Listener<T> listener,
										   ErrorListener errorListener, Activity activity) {

		APIRequestHelper.requestType = requestType;
		APIRequestHelper.clazz = clazz;
		APIRequestHelper.queryParams = queryParams;
		APIRequestHelper.listener = listener;
		APIRequestHelper.errorListener = errorListener;
		APIRequestHelper.activity = activity;
		APIRequestHelper.RequestType = Request.Method.GET;
		GsonGetRequest<T> mrequest = new GsonGetRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, null, listener,
				errorListener, activity);
		CeramicsApplication.getInstance().addToRequestQueue(mrequest);

	}

	private static <T> void userPostRequest(String requestType, Type clazz,
											JSONObject queryParams, Listener<T> listener,
											ErrorListener errorListener, Activity activity) {

		APIRequestHelper.requestType = requestType;
		APIRequestHelper.clazz = clazz;
		APIRequestHelper.queryParams = queryParams;
		APIRequestHelper.listener = listener;
		APIRequestHelper.errorListener = errorListener;
		APIRequestHelper.activity = activity;
		APIRequestHelper.RequestType = Request.Method.POST;
		Log.e("KK", "\n" + queryParams.toString());
		GsonPostRequest<T> mrequest = new GsonPostRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, listener, errorListener,
				activity);

		CeramicsApplication.getInstance().addToRequestQueue(mrequest);

	}


	private static <T> void userPutRequest(String requestType, Type clazz,
										   JSONObject queryParams, Listener<T> listener,
										   ErrorListener errorListener, Activity activity) {

		APIRequestHelper.requestType = requestType;
		APIRequestHelper.clazz = clazz;
		APIRequestHelper.queryParams = queryParams;
		APIRequestHelper.listener = listener;
		APIRequestHelper.errorListener = errorListener;
		APIRequestHelper.activity = activity;
		APIRequestHelper.RequestType = Request.Method.PUT;
		Log.e("KK", "\n" + queryParams.toString());
		GsonPutRequest<T> mrequest = new GsonPutRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, listener, errorListener,
				activity);

		CeramicsApplication.getInstance().addToRequestQueue(mrequest);

	}

	public static void callLastRequest() {

		if (RequestType == Request.Method.POST) {
			userPostRequest(requestType, clazz, queryParams, listener,
					errorListener, activity);
		}
		if (RequestType == Request.Method.GET) {
			userGetRequest(requestType, clazz, queryParams, listener,
					errorListener, activity);
		}
	}





	public static <T> void simpleJsonPostRequest(String url, Type clazz,
												 JSONObject queryParams, Listener<T> listener,
												 ErrorListener errListener) {

		GsonPostRequest<T> mrequest = new GsonPostRequest<T>(url, clazz,
				queryParams, listener, errListener);
		CeramicsApplication.getInstance().addToRequestQueue(mrequest);

	}


	/*************************************************************************************************/
	/***************************** Common API Call **************************************************/
	/*************************************************************************************************/


	public static <T> void kitchen(Type clazz, JSONObject queryParams, Listener<T> listener,
									   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/Kitchen", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void bathroom(Type clazz, JSONObject queryParams, Listener<T> listener,
								   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/Bathroom", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void bedroom(Type clazz, JSONObject queryParams, Listener<T> listener,
								   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/Bedroom", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void livingroom(Type clazz, JSONObject queryParams, Listener<T> listener,
								   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/livingroom", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void office(Type clazz, JSONObject queryParams, Listener<T> listener,
								   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/office", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void outdoor(Type clazz, JSONObject queryParams, Listener<T> listener,
								   ErrorListener errorListener, Activity activity) {
		userGetRequest("/Product/Category/outdoor", clazz, queryParams, listener, errorListener, activity);
	}


	public static <T> void getGoalList(Type clazz, JSONObject queryParams, Listener<T> listener,
									   ErrorListener errorListener, Activity activity) {
		userGetRequest("/account/goals", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void subscribeGoal(Type clazz, JSONObject queryParams, Listener<T> listener,
										 ErrorListener errorListener, Activity activity) {
		userPutRequest("/account/goals/subscribe", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void unSubscribeGoal(Type clazz, JSONObject queryParams, Listener<T> listener,
										   ErrorListener errorListener, Activity activity) {
		userPutRequest("/account/goals/unSubscribe", clazz, queryParams, listener, errorListener, activity);
	}

	public static <T> void achieveGoal(Type clazz, JSONObject queryParams, Listener<T> listener,
									   ErrorListener errorListener, Activity activity) {
		userPutRequest("/account/goals/achieve", clazz, queryParams, listener, errorListener, activity);
	}
}
