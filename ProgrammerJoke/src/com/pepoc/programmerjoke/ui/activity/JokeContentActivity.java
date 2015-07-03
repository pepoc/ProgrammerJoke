package com.pepoc.programmerjoke.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeComment;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.PImageLoader;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestComment;
import com.pepoc.programmerjoke.net.http.request.RequestGetComment;
import com.pepoc.programmerjoke.ui.adapter.JokeContentAdapter;

public class JokeContentActivity extends BaseActivity implements OnClickListener {

	private ImageView ivUserAvatar;
	private TextView tvUserName, tvContent;
	private JokeContent jokeContent;
	private ListView lvJokeComment;
	private View headerJokeContent;
	private EditText etJokeComment;
	private Button btnSendComment;
	private JokeContentAdapter jokeContentAdapter;

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
		jokeContentAdapter = new JokeContentAdapter(context);
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
		
		getComment();
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
	
	/**
	 * 发送评论
	 */
	private void comment() {
		RequestComment requestComment = new RequestComment(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				boolean isSuccess = (Boolean) result;
				if (isSuccess) {
					Toast.makeText(context, "comment success", Toast.LENGTH_SHORT).show();
					getComment();
				}
			}
		});
		
		String commentContent = etJokeComment.getText().toString();
		requestComment.putParam("content", commentContent);
		requestComment.putParam("jokeId", jokeContent.getJokeId());
		requestComment.putParam("userId", jokeContent.getUserId());
		
		HttpRequestManager.getInstance().sendRequest(requestComment);
	}
	
	/**
	 * 获取评论
	 */
	private void getComment() {
		RequestGetComment requestGetComment = new RequestGetComment(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				List<JokeComment> jokeComments = (List<JokeComment>) result;
				jokeContentAdapter.setJokeComments(jokeComments);
				jokeContentAdapter.notifyDataSetChanged();
			}
		});
		
		requestGetComment.putParam("jokeId", jokeContent.getJokeId());
		
		HttpRequestManager.getInstance().sendRequest(requestGetComment);
	}
	
}
