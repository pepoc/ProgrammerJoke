package com.pepoc.programmerjoke.ui.activity;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestAddJoke;
import com.pepoc.programmerjoke.user.UserManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PublishJokeActivity extends BaseActivity {
	
	private EditText etJokeContent;
	private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_joke);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		View publicTitle = findViewById(R.id.public_title);
		TextView tvMainFragmentTitle = (TextView) publicTitle.findViewById(R.id.tv_main_fragment_title);
		tvMainFragmentTitle.setText(R.string.activity_write_joke_name);
		
		etJokeContent = (EditText) findViewById(R.id.et_joke_content);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addJoke();
			}
		});
	}
	
	private void addJoke() {
		if (null == UserManager.getCurrentUser()) {
			Toast.makeText(context, "not login", Toast.LENGTH_SHORT).show();
			return ;
		}
		String content = etJokeContent.getText().toString();
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(context, "content null", Toast.LENGTH_SHORT).show();
			return ;
		}
		String uid = UserManager.getCurrentUser().getUserId();
		RequestAddJoke requestAddJoke = new RequestAddJoke(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				String status = (String) result;
				if ("1".equals(status)) {
					Toast.makeText(context, "send success", Toast.LENGTH_SHORT).show();
					etJokeContent.setText("");
				}
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		
		requestAddJoke.putParam("content", content);
		requestAddJoke.putParam("user_id", uid);
		
		HttpRequestManager.getInstance().sendRequest(requestAddJoke);
	}
}
