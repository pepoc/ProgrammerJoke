package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.fragment.MyFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentTabHost tabHostMain;
	private Button btnTest;
	private MyFragment myFragment;
	private PersonalCenterFragment centerFragment;
	private FragmentTransaction fragmentTransaction;

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
		
//		tabHostMain = (FragmentTabHost) findViewById(R.id.tab_host_main);
//		tabHostMain.setup(context, getSupportFragmentManager(), R.id.fl_content);
//		TabSpec tab1 = tabHostMain.newTabSpec("tab1").setIndicator("lala");
//		tabHostMain.addTab(tab1, MyFragment.class, null);
//		TabSpec tab2 = tabHostMain.newTabSpec("tab2").setIndicator("PersonalCenter");
//		tabHostMain.addTab(tab2, PersonalCenterFragment.class, null);
		
		btnTest = (Button) findViewById(R.id.btn_test);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		myFragment = new MyFragment();
		centerFragment = new PersonalCenterFragment();
		
		fragmentTransaction.add(R.id.fl_content, myFragment);
		fragmentTransaction.add(R.id.fl_content, centerFragment);
		fragmentTransaction.hide(centerFragment);
		fragmentTransaction.commit();
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnTest.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_test:
			fragmentTransaction.show(centerFragment);
			fragmentTransaction.hide(myFragment);
			break;

		default:
			break;
		}
	}

}
