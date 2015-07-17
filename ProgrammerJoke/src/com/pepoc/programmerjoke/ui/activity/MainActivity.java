package com.pepoc.programmerjoke.ui.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestLogin;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.ui.fragment.BaseFragment;
import com.pepoc.programmerjoke.ui.fragment.ListContentFragment;
import com.pepoc.programmerjoke.ui.fragment.MoreFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;
import com.pepoc.programmerjoke.utils.Preference;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentTabHost tabHostMain;
	private Map<String, Class<? extends BaseFragment>> fragmentMaps = new TreeMap<String, Class<? extends BaseFragment>>();
	private Map<String, Integer> titles = new HashMap<String, Integer>();
	private Map<String, Integer> tabIcons = new HashMap<String, Integer>();
	private Map<String, Integer> tabNames = new HashMap<String, Integer>();
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
		View publicTitle = findViewById(R.id.public_title);
		tvMainFragmentTitle = (TextView) publicTitle.findViewById(R.id.tv_main_fragment_title);
		tvMainFragmentTitle.setText(R.string.fragment_list_content_title);
		titles.put("tag1", R.string.fragment_list_content_title);
		titles.put("tag2", R.string.fragment_more);
		titles.put("tag3", R.string.fragment_personal_center);
		
		fragmentMaps.put("tag1", ListContentFragment.class);
		fragmentMaps.put("tag2", MoreFragment.class);
		fragmentMaps.put("tag3", PersonalCenterFragment.class);
		
		tabIcons.put("tag1", R.drawable.tab_index_selector);
		tabIcons.put("tag2", R.drawable.tab_more_selector);
		tabIcons.put("tag3", R.drawable.tab_personal_selector);
		
		tabNames.put("tag1", R.string.tab_name_index);
		tabNames.put("tag2", R.string.tab_name_more);
		tabNames.put("tag3", R.string.tab_name_personal);
		
		tabHostMain = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHostMain.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
		
		for (String key : fragmentMaps.keySet()) {
			View tabItem = View.inflate(context, R.layout.tab_item, null);
			ImageView ivTabIcon = (ImageView) tabItem.findViewById(R.id.iv_tab_icon);
			ivTabIcon.setImageResource(tabIcons.get(key));
			TextView tvTabName = (TextView) tabItem.findViewById(R.id.tv_tab_name);
			tvTabName.setText(tabNames.get(key));
			TabSpec tab = tabHostMain.newTabSpec(key).setIndicator(tabItem);
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
		final String accountNumber = Preference.getAccountNumber();
		final String password = Preference.getPassword();
		if (!Preference.isLogin() || TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(password)) {
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
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		
		requestLogin.putParam("accountNumber", accountNumber);
		requestLogin.putParam("password", password);
		
		HttpRequestManager.getInstance().sendRequest(requestLogin);
	}

}
