package com.pepoc.programmerjoke.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.activity.LoginActivity;

/**
 * 个人中心
 * @author yangchen
 *
 */
public class PersonalCenterFragment extends BaseFragment implements OnClickListener {
	
	private View rootView;
	private Button btnLogin;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_personal_center, null);
			
			init();
			setListener();
		}
		
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (null != parent) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	
	@Override
	public void init() {
		super.init();
		
		btnLogin = (Button) rootView.findViewById(R.id.btn_login);
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
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
