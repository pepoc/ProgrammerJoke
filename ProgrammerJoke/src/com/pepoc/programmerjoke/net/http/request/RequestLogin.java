package com.pepoc.programmerjoke.net.http.request;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestLogin extends HttpRequest {
	
	public RequestLogin(OnHttpResponseListener onHttpResponseListener) {
		this.requestMethod = METHOD_POST;
		this.URL = "login.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		return result;
	}

}
