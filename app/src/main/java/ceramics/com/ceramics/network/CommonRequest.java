package ceramics.com.ceramics.network;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CommonRequest<T> implements Serializable {

	/**
	 * 
	 */


	public CommonRequest(T data) {
		super();
		Data = data;
	}

	private T Data;

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

	public JSONObject getObject() {
		Gson gson = new Gson();
		try {
			return new JSONObject(gson.toJson(getData()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONObject();
	}



}
