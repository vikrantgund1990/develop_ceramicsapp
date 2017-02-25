package ceramics.com.ceramics.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONObject;

import java.lang.reflect.Type;

import demo.loylty.com.goalsapp.utils.GoalsAppApplication;


/**
 * @author siddhantp
 * 
 */
public class GoalsAppAPIRequestHelper {
	/******Live Url*****/
	//public static final String BASE_URL = "https://merchant.maxgetmore.com:8443";

//**	*//**//******QA Url*****//**//*
	public static final String BASE_URL = "http://workoutcashamarketing.in:8080/api";

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

		GoalsAppAPIRequestHelper.requestType = requestType;
		GoalsAppAPIRequestHelper.clazz = clazz;
		GoalsAppAPIRequestHelper.queryParams = queryParams;
		GoalsAppAPIRequestHelper.listener = listener;
		GoalsAppAPIRequestHelper.errorListener = errorListener;
		GoalsAppAPIRequestHelper.activity = activity;
		GoalsAppAPIRequestHelper.RequestType = Request.Method.GET;
		GsonGetRequest<T> mrequest = new GsonGetRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, null, listener,
				errorListener, activity);
		GoalsAppApplication.getInstance().addToRequestQueue(mrequest);

	}

	private static <T> void userPostRequest(String requestType, Type clazz,
											JSONObject queryParams, Listener<T> listener,
											ErrorListener errorListener, Activity activity) {

		GoalsAppAPIRequestHelper.requestType = requestType;
		GoalsAppAPIRequestHelper.clazz = clazz;
		GoalsAppAPIRequestHelper.queryParams = queryParams;
		GoalsAppAPIRequestHelper.listener = listener;
		GoalsAppAPIRequestHelper.errorListener = errorListener;
		GoalsAppAPIRequestHelper.activity = activity;
		GoalsAppAPIRequestHelper.RequestType = Request.Method.POST;
		Log.e("KK", "\n" + queryParams.toString());
		GsonPostRequest<T> mrequest = new GsonPostRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, listener, errorListener,
				activity);

		GoalsAppApplication.getInstance().addToRequestQueue(mrequest);

	}


	private static <T> void userPutRequest(String requestType, Type clazz,
										   JSONObject queryParams, Listener<T> listener,
										   ErrorListener errorListener, Activity activity) {

		GoalsAppAPIRequestHelper.requestType = requestType;
		GoalsAppAPIRequestHelper.clazz = clazz;
		GoalsAppAPIRequestHelper.queryParams = queryParams;
		GoalsAppAPIRequestHelper.listener = listener;
		GoalsAppAPIRequestHelper.errorListener = errorListener;
		GoalsAppAPIRequestHelper.activity = activity;
		GoalsAppAPIRequestHelper.RequestType = Request.Method.PUT;
		Log.e("KK", "\n" + queryParams.toString());
		GsonPutRequest<T> mrequest = new GsonPutRequest<T>(BASE_URL
				+ requestType, clazz, queryParams, listener, errorListener,
				activity);

		GoalsAppApplication.getInstance().addToRequestQueue(mrequest);

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
		GoalsAppApplication.getInstance().addToRequestQueue(mrequest);

	}


	/*************************************************************************************************/
	/***************************** Common API Call **************************************************/
	/*************************************************************************************************/


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
