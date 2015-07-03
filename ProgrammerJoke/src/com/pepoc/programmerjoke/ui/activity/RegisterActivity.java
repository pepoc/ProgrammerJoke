package com.pepoc.programmerjoke.ui.activity;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestRegister;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;

public class RegisterActivity extends BaseActivity implements OnClickListener, Observer {

	private EditText etPhoneNumber;
	private EditText etPassword;
	private EditText etNickName;
	private Button btnRegister;
	private String phoneNumber;
	private String password;
	private String nickName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		LoginObservable.getInstance().addObserver(this);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
		etPassword = (EditText) findViewById(R.id.et_password);
		etNickName = (EditText) findViewById(R.id.et_nick_name);
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
		phoneNumber = etPhoneNumber.getText().toString();
		password = etPassword.getText().toString();
		nickName = etNickName.getText().toString();
		RequestRegister requestRegister = new RequestRegister(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				try {
					JSONObject obj = new JSONObject((String)result);
					String status = obj.getString("status");
					if ("1".equals(status)) {
						String userId = obj.getString("userId");
					}
				} catch (JSONException e) {
					log.error("register", e);
				}
				LoginObservable.getInstance().updateObserver(null);
				
				Preference.saveUserId(UserManager.getCurrentUser().getUserId());
				Preference.saveIsLogin(true);
				Preference.savePhoneNumber(phoneNumber);
				Preference.savePassword(password);
			}
		});
		
		requestRegister.putParam("phone_number", phoneNumber);
		requestRegister.putParam("password", password);
		requestRegister.putParam("nickName", nickName);
		
		HttpRequestManager.getInstance().sendRequest(requestRegister);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		finish();
		
	}
	
}
