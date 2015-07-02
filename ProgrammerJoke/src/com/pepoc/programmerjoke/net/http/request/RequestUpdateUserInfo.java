package com.pepoc.programmerjoke.net.http.request;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestUpdateUserInfo extends HttpRequest {
	
	public RequestUpdateUserInfo(OnHttpResponseListener onHttpResponseListener) {
		this.URL = "updateuserinfo.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		return result;
	}

}
