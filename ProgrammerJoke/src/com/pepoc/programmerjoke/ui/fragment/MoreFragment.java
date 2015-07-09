package com.pepoc.programmerjoke.ui.fragment;

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
import com.pepoc.programmerjoke.user.UserManager;

/**
 * 写段子
 * @author yangchen
 *
 */
public class MoreFragment extends BaseFragment {
	
	private EditText etJokeContent;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_joke);
	}
	
	@Override
	public void init() {
		super.init();
		etJokeContent = (EditText) findViewById(R.id.et_joke_content);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null == UserManager.getCurrentUser()) {
					Toast.makeText(context, "not login", Toast.LENGTH_SHORT).show();
					return ;
				}
				String content = etJokeContent.getText().toString();
				addJoke(content, UserManager.getCurrentUser().getUserId());
			}
		});
	}
	
	private void addJoke(String content, String uid) {
		RequestAddJoke requestAddJoke = new RequestAddJoke(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				String status = (String) result;
				if ("1".equals(status)) {
					Toast.makeText(context, "send success", Toast.LENGTH_SHORT).show();
					etJokeContent.setText("");
				}
			}
		});
		
		requestAddJoke.putParam("content", content);
		requestAddJoke.putParam("user_id", uid);
		
		HttpRequestManager.getInstance().sendRequest(requestAddJoke);
	}
	
}
