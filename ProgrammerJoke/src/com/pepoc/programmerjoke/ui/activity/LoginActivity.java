package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestLogin;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText etPhoneNumber;
	private EditText etPassword;
	private Button btnLogin;

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
		
		etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;

		default:
			break;
		}
	}
	
	private void login() {
		String phoneNumber = etPhoneNumber.getText().toString();
		String password = etPassword.getText().toString();
		RequestLogin requestLogin = new RequestLogin(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		requestLogin.putParam("phone_number", phoneNumber);
		requestLogin.putParam("password", password);
		
		HttpRequestManager.getInstance().sendRequest(requestLogin);
	}
	
}
