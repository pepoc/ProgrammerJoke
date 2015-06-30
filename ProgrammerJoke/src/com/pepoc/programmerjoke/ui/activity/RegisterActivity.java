package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestRegister;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private EditText etPhoneNumber;
	private EditText etPassword;
	private Button btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnRegister = (Button) findViewById(R.id.btn_register);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnRegister.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			register();
			break;

		default:
			break;
		}
		
	}
	
	private void register() {
		String phoneNumber = etPhoneNumber.getText().toString();
		String password = etPassword.getText().toString();
		RequestRegister requestRegister = new RequestRegister(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		requestRegister.putParam("phone_number", phoneNumber);
		requestRegister.putParam("password", password);
		
		HttpRequestManager.getInstance().sendRequest(requestRegister);
	}
	
}
