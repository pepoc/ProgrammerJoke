package com.pepoc.programmerjoke.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.activity.LoginActivity;
import com.pepoc.programmerjoke.ui.activity.RegisterActivity;

/**
 * 个人中心
 * @author yangchen
 *
 */
public class PersonalCenterFragment extends BaseFragment implements OnClickListener {
	
	private Button btnLogin;
	private Button btn_register;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal_center);
	}
	
	@Override
	public void init() {
		super.init();
		
		btnLogin = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnLogin.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			Intent loginIntent = new Intent(context, LoginActivity.class);
			startActivity(loginIntent);
			break;
		case R.id.btn_register:
			Intent registerIntent = new Intent(context, RegisterActivity.class);
			startActivity(registerIntent);
			break;

		default:
			break;
		}
	}
	
}
