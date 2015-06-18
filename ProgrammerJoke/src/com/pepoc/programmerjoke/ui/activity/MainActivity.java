package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.fragment.ListContentFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

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
		
		tabHostMain = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHostMain.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
		
		ImageView imageView = new ImageView(context);
		imageView.setBackgroundResource(R.drawable.ic_launcher);
		TabSpec tab1 = tabHostMain.newTabSpec("tab1").setIndicator(imageView);
		tabHostMain.addTab(tab1, ListContentFragment.class, null);
		
		ImageView imageView1 = new ImageView(context);
		imageView1.setBackgroundResource(R.drawable.ic_launcher);
		TabSpec tab2 = tabHostMain.newTabSpec("tab2").setIndicator(imageView1);
		tabHostMain.addTab(tab2, PersonalCenterFragment.class, null);
		
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

}
