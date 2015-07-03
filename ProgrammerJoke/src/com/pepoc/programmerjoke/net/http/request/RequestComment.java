package com.pepoc.programmerjoke.net.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestComment extends HttpRequest {
	
	public RequestComment(OnHttpResponseListener onHttpResponseListener) {
		this.requestMethod = METHOD_POST;
		this.URL = "comment.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		try {
			JSONObject obj = new JSONObject(result);
			String status = obj.getString("status");
			if ("1".equals(status)) {
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			log.error("parseResponseResult", e);
		}
		return result;
	}

}
