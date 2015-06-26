package com.pepoc.programmerjoke.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;

import com.pepoc.programmerjoke.log.Log;
import com.pepoc.programmerjoke.log.LogFactory;

public class BaseFragmentActivity extends FragmentActivity {
	
	public final Log log = LogFactory.getLog(this.getClass());
	public Context context;
	public int mScreenWidth;
	public int mScreenHeight;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}
	
	public void init() {

	}
	
	public void setListener() {

	}
}
