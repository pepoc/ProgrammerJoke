package com.pepoc.programmerjoke.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestListContent;
import com.pepoc.programmerjoke.ui.activity.JokeContentActivity;
import com.pepoc.programmerjoke.ui.adapter.ListContentAdapter;

/**
 * 段子列表页
 * @author yangchen
 *
 */
public class ListContentFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener2<ListView> {
	
	private PullToRefreshListView mPullRefreshListView;
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
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_content_list_refresh);
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("lalala");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("hahaha");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("heihei");
		mPullRefreshListView.setPullToRefreshOverScrollEnabled(false);
		lvContentList = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setOnRefreshListener(this);
		adapter = new ListContentAdapter(context);
		lvContentList.setAdapter(adapter);
		
		getData();
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		lvContentList.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		List<JokeContent> datas = adapter.getDatas();
		Intent intent = new Intent(context, JokeContentActivity.class);
		intent.putExtra("JokeContent", datas.get(position));
		startActivity(intent);
	}
	
	private void getData() {
		
		RequestListContent request = new RequestListContent(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				adapter.setDatas((List<JokeContent>) result);
				adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
			}
		});
		
		HttpRequestManager.getInstance().sendRequest(request);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		
	}

}
