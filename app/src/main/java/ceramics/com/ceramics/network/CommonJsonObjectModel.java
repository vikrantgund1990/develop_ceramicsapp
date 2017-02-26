package ceramics.com.ceramics.network;

import java.io.Serializable;

public class CommonJsonObjectModel<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7218340084773923544L;
	/**
	 * 
	 */

	private int appStatusCode;
	private T data;
	private boolean status;
	private String message;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getAppStatusCode() {
		return appStatusCode;
	}

	public void setAppStatusCode(int appStatusCode) {
		this.appStatusCode = appStatusCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
