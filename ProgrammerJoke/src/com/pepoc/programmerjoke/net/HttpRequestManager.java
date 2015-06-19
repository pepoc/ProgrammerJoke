package com.pepoc.programmerjoke.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpRequestManager {
	
	private static HttpRequestManager instance = null;
	private RequestQueue mRequestQueue;
	private Context context;
	
	private HttpRequestManager() {
		mRequestQueue = Volley.newRequestQueue(context);
	}
	
	public static HttpRequestManager getInstance() {
		if (null == instance) {
			instance = new HttpRequestManager();
		}
		return instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void sendRequest(HttpRequest request) {
		if (HttpRequest.METHOD_GET.equals(request.getRequestMethod())) {
			sendGetRequest(createGetRequestUrl(request), new Listener<String>() {

				@Override
				public void onResponse(String response) {
					
				}
				
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					
				}
			});
		}
		
	}
	
	private void sendGetRequest(String url, Listener<String> listener, ErrorListener errorListener) {
		StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener);
		mRequestQueue.add(request);
	}
	
	private void sendPostRequest() {
//		StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener);
	}
	
	private String createGetRequestUrl(HttpRequest request) {
		
		return null;
	}
	
}
