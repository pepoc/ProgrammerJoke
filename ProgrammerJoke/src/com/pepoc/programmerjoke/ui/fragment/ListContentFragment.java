package com.pepoc.programmerjoke.ui.fragment;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestListContent;
import com.pepoc.programmerjoke.ui.adapter.ListContentAdapter;

/**
 * 段子列表页
 * @author yangchen
 *
 */
public class ListContentFragment extends BaseFragment {
	
	private ListView lvContentList;
	private ListContentAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list_content);
	}
	
	@Override
	public void init() {
		super.init();
		
		lvContentList = (ListView) findViewById(R.id.lv_content_list);
		adapter = new ListContentAdapter(context);
		lvContentList.setAdapter(adapter);
		
		getData();
	}
	
	@Override
	public void setListener() {
		super.setListener();
	}
	
	private void getData() {
		
		RequestListContent request = new RequestListContent(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				adapter.setDatas((List<JokeContent>) result);
				adapter.notifyDataSetChanged();
			}
		});
		
		HttpRequestManager.getInstance().sendRequest(request);
	}
}
