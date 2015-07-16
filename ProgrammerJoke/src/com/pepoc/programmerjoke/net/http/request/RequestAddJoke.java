package com.pepoc.programmerjoke.net.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestAddJoke extends HttpRequest {
	
	public RequestAddJoke(OnHttpResponseListener onHttpResponseListener) {
		this.requestMethod = METHOD_POST;
		this.URL = "addjoke.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		try {
			JSONObject obj = new JSONObject(result);
			String status = obj.getString("status");
			return status;
		} catch (JSONException e) {
			log.e("parseResponseResult", e);
		}
		return "0";
	}

}
