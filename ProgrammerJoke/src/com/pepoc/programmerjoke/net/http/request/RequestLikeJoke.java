package com.pepoc.programmerjoke.net.http.request;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestLikeJoke extends HttpRequest {
	
	public RequestLikeJoke(OnHttpResponseListener onHttpResponseListener) {
		this.URL = "likejoke.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		return result;
	}

}
