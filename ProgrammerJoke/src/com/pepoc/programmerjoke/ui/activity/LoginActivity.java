package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pepoc.programmerjoke.R;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private Button btnWeiboLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		btnWeiboLogin = (Button) findViewById(R.id.btn_weibo_login);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnWeiboLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_weibo_login:
			break;

		default:
			break;
		}
	}
	
}
