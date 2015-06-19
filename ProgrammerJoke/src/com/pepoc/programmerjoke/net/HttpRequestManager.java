package com.pepoc.programmerjoke.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pepoc.programmerjoke.log.Log;
import com.pepoc.programmerjoke.log.LogFactory;

/**
 * Http请求管理器
 * @author yangchen
 *
 */
public class HttpRequestManager {
	
	public final Log log = LogFactory.getLog(this.getClass());
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

	/**
	 * 发送Http请求
	 * @param request
	 */
	public void sendRequest(final HttpRequest request) {
		if (HttpRequest.METHOD_GET.equals(request.getRequestMethod())) {
			sendGetRequest(createGetRequestUrl(request), new Listener<String>() {

				@Override
				public void onResponse(String response) {
					log.info("<<<<<<<<<<<<<<< Http response result <<<<<<<<<<<<<<<");
					log.info(response);
					Object result = request.parseResponseResult(response);
					request.getOnHttpResponseListener().onHttpResponse(result);
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
	
	/**
	 * 组合一个get请求串
	 * @param request
	 * @return
	 */
	private String createGetRequestUrl(HttpRequest request) {
		
		return null;
	}
	
	public interface OnHttpResponseListener {
		void onHttpResponse(Object result);
	}
	
}
