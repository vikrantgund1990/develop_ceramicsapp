package ceramics.com.ceramics.network;

import java.io.Serializable;
import java.util.ArrayList;

public class CommonJsonArrayModel<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8406847844307220972L;

	private int appStatusCode;
	private ArrayList<T> data;

	public ArrayList<T> getData() {
		return data;
	}

	public void setData(ArrayList<T> data) {
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
