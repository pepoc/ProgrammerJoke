package com.pepoc.programmerjoke.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestAddJoke;
import com.pepoc.programmerjoke.ui.activity.WriteJokeActivity;
import com.pepoc.programmerjoke.user.UserManager;

/**
 * 写段子
 * @author yangchen
 *
 */
public class MoreFragment extends BaseFragment implements OnClickListener {

	private Button btnWriteJoke;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_more);
	}
	
	@Override
	public void init() {
		super.init();
		
		btnWriteJoke = (Button) findViewById(R.id.btn_write_joke);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnWriteJoke.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_write_joke:
			Intent intent = new Intent(context, WriteJokeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
