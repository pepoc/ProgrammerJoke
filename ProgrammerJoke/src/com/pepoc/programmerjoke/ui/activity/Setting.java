package com.pepoc.programmerjoke.ui.activity;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.constants.Constant;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends BaseActivity implements OnClickListener {
	
	private Button btnLogout;
	private TextView tvMainFragmentTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		View publicTitle = findViewById(R.id.public_title);
		tvMainFragmentTitle = (TextView) publicTitle.findViewById(R.id.tv_main_fragment_title);
		tvMainFragmentTitle.setText(R.string.activity_setting_name);
		
		btnLogout = (Button) findViewById(R.id.btn_logout);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		btnLogout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_logout:
			UserManager.setCurrentUser(null);
			Preference.saveIsLogin(false);
			setResult(Constant.RESULT_LOGOUT_OK);
			finish();
			break;

		default:
			break;
		}
	}
	
}
