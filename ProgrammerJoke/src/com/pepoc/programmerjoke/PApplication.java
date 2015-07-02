package com.pepoc.programmerjoke;

import com.pepoc.programmerjoke.net.http.HttpRequestManager;

import android.app.Application;
import android.content.Context;

public class PApplication extends Application {
	
	private static PApplication instance = null;
	private Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		instance = this;
		initHttpManager();
	}
	
	private void initHttpManager() {
		HttpRequestManager.getInstance().init(context);
	}
	
	public static PApplication getInstance() {
		return instance;
	}
	
}
