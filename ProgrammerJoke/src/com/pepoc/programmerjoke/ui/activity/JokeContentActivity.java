package com.pepoc.programmerjoke.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeComment;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.PImageLoader;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestCollectJoke;
import com.pepoc.programmerjoke.net.http.request.RequestComment;
import com.pepoc.programmerjoke.net.http.request.RequestGetComment;
import com.pepoc.programmerjoke.net.http.request.RequestLikeJoke;
import com.pepoc.programmerjoke.ui.adapter.JokeContentAdapter;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;
import com.pepoc.programmerjoke.utils.Util;

public class JokeContentActivity extends BaseActivity implements OnClickListener {

	private ImageView ivUserAvatar;
	private TextView tvUserName, tvContent;
	private JokeContent jokeContent;
	private PullToRefreshListView mPullRefreshListView;
	private ListView lvJokeComment;
	private View headerJokeContent;
	private EditText etJokeComment;
	private Button btnSendComment;
	private JokeContentAdapter jokeContentAdapter;
	private Button btnCollectJoke, btnLikeJoke;

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
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_joke_comment_list_refresh);
		mPullRefreshListView.setPullToRefreshOverScrollEnabled(false);
		lvJokeComment = mPullRefreshListView.getRefreshableView();
		headerJokeContent = View.inflate(context, R.layout.header_joke_content, null);
		lvJokeComment.addHeaderView(headerJokeContent);
		jokeContentAdapter = new JokeContentAdapter(context);
		lvJokeComment.setAdapter(jokeContentAdapter);
		
		ivUserAvatar = (ImageView) headerJokeContent.findViewById(R.id.iv_user_avatar);
		tvUserName = (TextView) headerJokeContent.findViewById(R.id.tv_user_name);
		tvContent = (TextView) headerJokeContent.findViewById(R.id.tv_content);
		btnCollectJoke = (Button) headerJokeContent.findViewById(R.id.btn_collect_joke);
		btnLikeJoke = (Button) headerJokeContent.findViewById(R.id.btn_like_joke);
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
		btnCollectJoke.setOnClickListener(this);
		btnLikeJoke.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_comment:
			if (Preference.isLogin()) {
				String commentContent = etJokeComment.getText().toString();
				if (TextUtils.isEmpty(commentContent)) {
					Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
				} else {
					comment(commentContent);
				}
			} else {
				Toast.makeText(context, "登录后才能评论", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_collect_joke:
			collectJoke();
			break;
		case R.id.btn_like_joke:
			likeJoke();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 发送评论
	 */
	private void comment(String commentContent) {
		RequestComment request = new RequestComment(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				boolean isSuccess = (Boolean) result;
				if (isSuccess) {
					Toast.makeText(context, "comment success", Toast.LENGTH_SHORT).show();
					etJokeComment.setText("");
					Util.hiddenSoftKeyborad(etJokeComment, context);
					getComment();
				}
			}
		});
		
		request.putParam("content", commentContent);
		request.putParam("jokeId", jokeContent.getJokeId());
		request.putParam("userId", UserManager.getCurrentUser().getUserId());
		
		HttpRequestManager.getInstance().sendRequest(request);
	}
	
	/**
	 * 获取评论
	 */
	private void getComment() {
		RequestGetComment request = new RequestGetComment(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				List<JokeComment> jokeComments = (List<JokeComment>) result;
				jokeContentAdapter.setJokeComments(jokeComments);
				jokeContentAdapter.notifyDataSetChanged();
			}
		});
		
		request.putParam("jokeId", jokeContent.getJokeId());
		
		HttpRequestManager.getInstance().sendRequest(request);
	}
	
	private void collectJoke() {
		RequestCollectJoke request = new RequestCollectJoke(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		request.putParam("jokeId", jokeContent.getJokeId());
		request.putParam("userId", UserManager.getCurrentUser().getUserId());
		HttpRequestManager.getInstance().sendRequest(request);
	}
	
	private void likeJoke() {
		RequestLikeJoke request = new RequestLikeJoke(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		request.putParam("jokeId", jokeContent.getJokeId());
		request.putParam("userId", UserManager.getCurrentUser().getUserId());
		HttpRequestManager.getInstance().sendRequest(request);
	}
	
}
