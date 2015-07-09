package com.pepoc.programmerjoke.ui.activity;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestGetEmailCaptcha;
import com.pepoc.programmerjoke.net.http.request.RequestRegister;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.user.UserInfo;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;

public class RegisterActivity extends BaseActivity implements OnClickListener, Observer {

	private EditText etAccountNumber;
	private EditText etPassword;
	private EditText etNickName;
	private Button btnRegister;
	private String accountNumber;
	private String registerCaptcha;
	private String password;
	private String nickName;
	private EditText etRegisterCaptcha;
	private Button btnGetCaptcha;

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
		etAccountNumber = (EditText) findViewById(R.id.et_account_number);
		etRegisterCaptcha = (EditText) findViewById(R.id.et_register_captcha);
		btnGetCaptcha = (Button) findViewById(R.id.btn_get_captcha);
		etPassword = (EditText) findViewById(R.id.et_password);
		etNickName = (EditText) findViewById(R.id.et_nick_name);
		btnRegister = (Button) findViewById(R.id.btn_register);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnGetCaptcha.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		
		etAccountNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (etAccountNumber.hasFocus()) {
					log.info("哈哈   拿到焦点了");
				} else {
					log.info("焦点没了");
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_captcha:
			getEmailCaptcha();
			break;
		case R.id.btn_register:
			register();
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 获取邮件验证码
	 */
	private void getEmailCaptcha() {
		accountNumber = etAccountNumber.getText().toString();
		if (TextUtils.isEmpty(accountNumber)) {
			return ;
		}
		RequestGetEmailCaptcha requestGetEmailCaptcha = new RequestGetEmailCaptcha(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		requestGetEmailCaptcha.putParam("email", accountNumber);
		requestGetEmailCaptcha.putParam("type", "0");
		
		HttpRequestManager.getInstance().sendRequest(requestGetEmailCaptcha);
	}
	
	private void register() {
		accountNumber = etAccountNumber.getText().toString();
		registerCaptcha = etRegisterCaptcha.getText().toString();
		password = etPassword.getText().toString();
		nickName = etNickName.getText().toString();
		if (TextUtils.isEmpty(accountNumber)) {
			Toast.makeText(context, "Account Number null", Toast.LENGTH_SHORT).show();
			return ;
		} else if (TextUtils.isEmpty(registerCaptcha)) {
			
		} else if (TextUtils.isEmpty(password)) {
			
		} else if (TextUtils.isEmpty(nickName)) {
			
		}
		RequestRegister requestRegister = new RequestRegister(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				try {
					JSONObject obj = new JSONObject((String)result);
					String status = obj.getString("status");
					if ("1".equals(status)) {
						String userId = obj.getString("userId");
						log.info("userId ============ " + userId);
						UserInfo info = new UserInfo();
						info.setUserId(userId);
						info.setNickName(nickName);
						info.setAccountNumber(accountNumber);
						info.setPassword(password);
						UserManager.setCurrentUser(info);
						
						LoginObservable.getInstance().updateObserver(null);
						Preference.saveIsLogin(true);
					}
				} catch (JSONException e) {
					log.error("register", e);
				}
			}
		});
		
		requestRegister.putParam("accountNumber", accountNumber);
		requestRegister.putParam("captcha", registerCaptcha);
		requestRegister.putParam("password", password);
		requestRegister.putParam("nickName", nickName);
		
		HttpRequestManager.getInstance().sendRequest(requestRegister);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		finish();
		
	}
	
}
