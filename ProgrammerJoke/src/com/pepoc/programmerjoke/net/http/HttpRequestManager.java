package com.pepoc.programmerjoke.net.http;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
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
		
	}
	
	public static HttpRequestManager getInstance() {
		if (null == instance) {
			instance = new HttpRequestManager();
		}
		return instance;
	}

	public void init(Context context) {
		this.context = context;
		mRequestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * 发送Http请求
	 * @param request
	 */
	public void sendRequest(final HttpRequest request) {
		String requestUrl = createGetRequestUrl(request);
		log.info("Http request url >>>>>>>>>>>>>>> " + requestUrl);
		
		if (HttpRequest.METHOD_GET.equals(request.getRequestMethod())) {
			sendGetRequest(requestUrl, new Listener<String>() {

				@Override
				public void onResponse(String response) {
					log.info("Http response result <<<<<<<<<<<<<<< " + response);
					Object result = request.parseResponseResult(response);
					request.getOnHttpResponseListener().onHttpResponse(result);
				}
				
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					
				}
			});
		} else if (HttpRequest.METHOD_POST.equals(request.getRequestMethod())) {
			sendPostRequest(request.getURL(), new Listener<String>() {

				@Override
				public void onResponse(String response) {
					log.info("Http response result <<<<<<<<<<<<<<< " + response);
					Object result = request.parseResponseResult(response);
					request.getOnHttpResponseListener().onHttpResponse(result);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					
				}
			}, request.getParams());
		}
		
	}
	
	/**
	 * 发送Get请求
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	private void sendGetRequest(String url, Listener<String> listener, ErrorListener errorListener) {
		StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener);
		mRequestQueue.add(request);
	}
	
	/**
	 * 发送Post请求
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @param params
	 */
	private void sendPostRequest(String url, Listener<String> listener, ErrorListener errorListener, final Map<String, String> params) {
		StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		mRequestQueue.add(request);
	}
	
	/**
	 * 组合一个get请求串
	 * @param request
	 * @return
	 */
	private String createGetRequestUrl(HttpRequest request) {
		String requestUrl = request.getURL();
		Map<String, String> params = request.getParams();
		if (params.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(requestUrl).append("?");
			for (String key : params.keySet()) {
				sb.append(key + "=" + params.get(key) + "&");
			}
			String str = sb.toString();
			requestUrl = str.substring(0, str.length() - 1);
		}
		return requestUrl;
	}
	
	public interface OnHttpResponseListener {
		void onHttpResponse(Object result);
	}
	
}
