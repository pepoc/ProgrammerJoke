package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.pepoc.programmerjoke.R;

public class MainActivity extends BaseFragmentActivity {

	private FragmentTabHost tabHostMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		tabHostMain = (FragmentTabHost) findViewById(R.id.tab_host_main);
		tabHostMain.setup(context, getSupportFragmentManager());
//		tabHostMain.new
	}
	
	@Override
	public void setListener() {
		super.setListener();
	}

}
