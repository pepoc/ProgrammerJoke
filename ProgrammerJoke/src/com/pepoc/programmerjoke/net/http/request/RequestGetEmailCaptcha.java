package com.pepoc.programmerjoke.net.http.request;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestGetEmailCaptcha extends HttpRequest {
	
	public RequestGetEmailCaptcha(OnHttpResponseListener onHttpResponseListener) {
		this.URL = "sendemailcaptcha.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		return result;
	}

}
