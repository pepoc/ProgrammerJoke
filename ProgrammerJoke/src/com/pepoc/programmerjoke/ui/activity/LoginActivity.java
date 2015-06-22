package com.pepoc.programmerjoke.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.pepoc.programmerjoke.Config;
import com.pepoc.programmerjoke.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private Button btnWeiboLogin;
	private AuthInfo mAuthInfo;
	private SsoHandler mSsoHandler;
	
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

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
			initWeiboLoginInfo();
			break;

		default:
			break;
		}
	}
	
	private void initWeiboLoginInfo() {
		mAuthInfo = new AuthInfo(context, Config.APP_KEY, Config.REDIRECT_URL, Config.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
		
		mSsoHandler.authorize(new AuthListener());
	}
	
	/**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
        	log.info("values.toString() === " + values.toString());
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息 
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
//                updateTokenView(false);
                
                // 保存 Token 到 SharedPreferences
//                AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);
//                Toast.makeText(WBAuthActivity.this, 
//                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
//                String code = values.getString("code");
//                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
//                if (!TextUtils.isEmpty(code)) {
//                    message = message + "\nObtained the code: " + code;
//                }
//                Toast.makeText(WBAuthActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
//            Toast.makeText(WBAuthActivity.this, 
//                   R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
//            Toast.makeText(WBAuthActivity.this, 
//                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
	
}
