package com.pepoc.programmerjoke.net.http;

import java.util.HashMap;

import com.pepoc.programmerjoke.Config;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public abstract class HttpRequest {

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	/**
	 * 默认Http请求方式为get
	 */
	protected String requestMethod = METHOD_GET;
	protected String URL = null;
	private HashMap<String, String> params = new HashMap<String, String>();
	public OnHttpResponseListener onHttpResponseListener;
	
	/**
	 * 获取请求Url
	 * @return
	 */
	public String getURL() {
		return Config.HOST + URL;
	}
	
	/**
	 * 获取Http请求类型  get post or other
	 * @return
	 */
	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求参数
	 * @param key
	 * @param value
	 */
	public void addParam(String key, String value) {
		params.put(key, value);
	}

	/**
	 * 获取请求参数
	 * @return
	 */
	public HashMap<String, String> getParams() {
		return params;
	}
	
	public OnHttpResponseListener getOnHttpResponseListener() {
		return onHttpResponseListener;
	}

	/**
	 * 解析服务器返回数据
	 * @param result
	 */
	public abstract Object parseResponseResult(String result);
	
}
