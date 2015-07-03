package com.pepoc.programmerjoke.ui.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestLogin;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.ui.fragment.BaseFragment;
import com.pepoc.programmerjoke.ui.fragment.ListContentFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;
import com.pepoc.programmerjoke.ui.fragment.WriteJokeFragment;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentTabHost tabHostMain;
	private Map<String, Class<? extends BaseFragment>> fragmentMaps = new HashMap<String, Class<? extends BaseFragment>>();
	private Map<String, Integer> titles = new HashMap<String, Integer>();
	private TextView tvMainFragmentTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		init();
		setListener();
		
		autoLogin();
	}
	
	@Override
	public void init() {
		super.init();
		tvMainFragmentTitle = (TextView) findViewById(R.id.tv_main_fragment_title);
		
		titles.put("tag1", R.string.fragment_list_content_title);
		titles.put("tag2", R.string.fragment_write_joke_title);
		titles.put("tag3", R.string.fragment_personal_center);
		
		fragmentMaps.put("tag1", ListContentFragment.class);
		fragmentMaps.put("tag2", WriteJokeFragment.class);
		fragmentMaps.put("tag3", PersonalCenterFragment.class);
		
		tabHostMain = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHostMain.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
		
		for (String key : fragmentMaps.keySet()) {
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.ic_launcher);
			TabSpec tab = tabHostMain.newTabSpec(key).setIndicator(imageView);
			tabHostMain.addTab(tab, fragmentMaps.get(key), null);
		}
		
		tabHostMain.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				int strId = titles.get(tabId).intValue();
				tvMainFragmentTitle.setText(strId);
			}
		});
		
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
	
	private void autoLogin() {
		final String phoneNumber = Preference.getPhoneNumber();
		final String password = Preference.getPassword();
		if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)) {
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
		
		requestLogin.putParam("phoneNumber", phoneNumber);
		requestLogin.putParam("password", password);
		
		HttpRequestManager.getInstance().sendRequest(requestLogin);
	}

}
