package com.pepoc.programmerjoke.ui.activity;

import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestLogin;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;

public class LoginActivity extends BaseActivity implements OnClickListener, Observer {

	private EditText etAccountNumber;
	private EditText etPassword;
	private Button btnLogin;
	private String accountNumber, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		LoginObservable.getInstance().addObserver(this);
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		etAccountNumber = (EditText) findViewById(R.id.et_account_number);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		
		String phoneNumber = Preference.getAccountNumber();
		String password = Preference.getPassword();
		
		if (!TextUtils.isEmpty(phoneNumber)) {
			etAccountNumber.setText(phoneNumber);
		}
		if (!TextUtils.isEmpty(password)) {
			etPassword.setText(password);
		}
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
		accountNumber = etAccountNumber.getText().toString();
		password = etPassword.getText().toString();
		if (TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(password)) {
			Toast.makeText(context, "account number or password null", Toast.LENGTH_SHORT).show();
			return ;
		}
		RequestLogin requestLogin = new RequestLogin(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				boolean isLoginSuccess = (Boolean) result;
				if (isLoginSuccess) {
					Toast.makeText(context, "login success", Toast.LENGTH_SHORT).show();
					LoginObservable.getInstance().updateObserver(null);
					Preference.saveIsLogin(true);
					
				} else {
					Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		requestLogin.putParam("accountNumber", accountNumber);
		requestLogin.putParam("password", password);
		
		HttpRequestManager.getInstance().sendRequest(requestLogin);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		finish();
	}
	
}
