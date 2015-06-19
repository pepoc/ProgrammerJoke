package com.pepoc.programmerjoke.net;

import java.util.HashMap;

public abstract class HttpRequest {

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	public String URL = null;
	public String requestMethod = METHOD_POST;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	public String getURL() {
		return URL;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public void addParam(String key, String value) {
		params.put(key, value);
	}

	public HashMap<String, String> getParams() {
		return params;
	}
	
}
