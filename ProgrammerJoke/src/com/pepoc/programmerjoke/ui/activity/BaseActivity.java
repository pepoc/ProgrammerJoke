package com.pepoc.programmerjoke.ui.activity;

import com.pepoc.programmerjoke.log.Log;
import com.pepoc.programmerjoke.log.LogFactory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {
	
	public final Log log = LogFactory.getLog(this.getClass());
	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public void init() {
		// TODO Auto-generated method stub

	}
	
	public void setListener() {
		// TODO Auto-generated method stub

	}
}
