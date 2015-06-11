package com.pepoc.programmerjoke.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseFragmentActivity extends FragmentActivity {
	
	public Context context;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public void init() {

	}
	
	public void setListener() {

	}
}
