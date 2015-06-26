package com.pepoc.programmerjoke.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestAddJoke;

/**
 * 写段子
 * @author yangchen
 *
 */
public class WriteJokeFragment extends BaseFragment {
	
	private View rootView;
	private EditText etJokeContent;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_write_joke, null);
			
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
		etJokeContent = (EditText) rootView.findViewById(R.id.et_joke_content);
		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = etJokeContent.getText().toString();
				addJoke(content, "1");
			}
		});
	}
	
	private void addJoke(String content, String uid) {
		RequestAddJoke requestAddJoke = new RequestAddJoke(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				
			}
		});
		
		requestAddJoke.putParam("content", content);
		requestAddJoke.putParam("user_id", uid);
		
		HttpRequestManager.getInstance().sendRequest(requestAddJoke);
	}
	
}
