package com.pepoc.programmerjoke.net.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestRegister extends HttpRequest {
	
	public RequestRegister(OnHttpResponseListener onHttpResponseListener) {
		this.requestMethod = METHOD_POST;
		this.URL = "register.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		
		return result;
	}

}
