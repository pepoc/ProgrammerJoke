package com.pepoc.programmerjoke;

import com.pepoc.programmerjoke.net.HttpRequestManager;

import android.app.Application;
import android.content.Context;

public class PApplication extends Application {
	
	private Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		
		HttpRequestManager.getInstance().setContext(context);
	}
}