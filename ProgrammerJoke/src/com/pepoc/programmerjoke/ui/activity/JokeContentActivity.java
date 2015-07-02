package com.pepoc.programmerjoke.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.PImageLoader;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestComment;
import com.pepoc.programmerjoke.ui.adapter.JokeContentAdapter;

public class JokeContentActivity extends BaseActivity implements OnClickListener {

	private ImageView ivUserAvatar;
	private TextView tvUserName, tvContent;
	private JokeContent jokeContent;
	private ListView lvJokeComment;
	private View headerJokeContent;
	private EditText etJokeComment;
	private Button btnSendComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_joke_content);
		
		Intent intent = getIntent();
		jokeContent = intent.getParcelableExtra("JokeContent");
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		lvJokeComment = (ListView) findViewById(R.id.lv_joke_comment);
		headerJokeContent = View.inflate(context, R.layout.header_joke_content, null);
		lvJokeComment.addHeaderView(headerJokeContent);
		JokeContentAdapter jokeContentAdapter = new JokeContentAdapter();
		lvJokeComment.setAdapter(jokeContentAdapter);
		jokeContentAdapter.notifyDataSetChanged();
		
		ivUserAvatar = (ImageView) headerJokeContent.findViewById(R.id.iv_user_avatar);
		tvUserName = (TextView) headerJokeContent.findViewById(R.id.tv_user_name);
		tvContent = (TextView) headerJokeContent.findViewById(R.id.tv_content);
		etJokeComment = (EditText) findViewById(R.id.et_joke_comment);
		btnSendComment = (Button) findViewById(R.id.btn_send_comment);
		
		PImageLoader.getInstance().displayImage(jokeContent.getUserAvatar(), ivUserAvatar);
		tvUserName.setText(jokeContent.getUserNickName());
		tvContent.setText(jokeContent.getContent());
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnSendComment.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_comment:
			comment();
			break;

		default:
			break;
		}
	}
	
	private void comment() {
		RequestComment requestComment = new RequestComment(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		String commentContent = etJokeComment.getText().toString();
		requestComment.putParam("content", commentContent);
		requestComment.putParam("jokeId", jokeContent.getJokeId());
		requestComment.putParam("userId", jokeContent.getUserId());
		
		HttpRequestManager.getInstance().sendRequest(requestComment);
	}
	
}
