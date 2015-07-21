package com.pepoc.programmerjoke.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestGetPublishedJokes;
import com.pepoc.programmerjoke.ui.adapter.ListContentAdapter;
import com.pepoc.programmerjoke.user.UserManager;

public class PublishedJokeActivity extends BaseActivity implements OnItemClickListener, OnScrollListener, SwipeRefreshLayout.OnRefreshListener {
	
	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView lvContentList;
	private ListContentAdapter adapter;
	private int page = 1;
	private View footerView;
	private TextView tvMainFragmentTitle;
	
	/** 是否还有更多数据 */
	private boolean isHasMoreData = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_published_joke);
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		View publicTitle = findViewById(R.id.public_title);
		tvMainFragmentTitle = (TextView) publicTitle.findViewById(R.id.tv_main_fragment_title);
		tvMainFragmentTitle.setText(R.string.activity_published_name);
		
		swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setColorSchemeResources(android.R.color.background_dark
                , android.R.color.black, android.R.color.black, android.R.color.background_dark);
		lvContentList = (ListView) findViewById(R.id.lv_published_list_refresh);
		adapter = new ListContentAdapter(context);
		lvContentList.setAdapter(adapter);
		
		footerView = View.inflate(context, R.layout.footer_view, null);
		footerView.setVisibility(View.GONE);
		View ivProgressLoad = footerView.findViewById(R.id.iv_progress_load);
//		ivProgressLoad.setOnClickListener(this);
		lvContentList.addFooterView(footerView, null, false);
		
		getData(true);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		swipeRefreshLayout.setOnRefreshListener(this);
		lvContentList.setOnItemClickListener(this);
		lvContentList.setOnScrollListener(this);
	}
	
	@Override
	public void onRefresh() {
		getData(true);
	}
	
	/**
	 * 获取数据
	 * @param isRefresh true:是刷新     false:加载更多
	 */
	private void getData(final boolean isRefresh) {
		if (isRefresh) {
			page = 1;
			isHasMoreData = true;
		} else {
			if (!isHasMoreData) {
//				Toast.makeText(context, "没有更多的数据了", Toast.LENGTH_SHORT).show();
				return ;
			}
			page++;
		}
		
		RequestGetPublishedJokes request = new RequestGetPublishedJokes(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				List<JokeContent> datas = (List<JokeContent>) result;
				if (datas.size() < 20) {
					isHasMoreData = false;
				}
				if (isRefresh) {
					adapter.getDatas().clear();
				}
				adapter.setDatas(datas);
				adapter.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);
				footerView.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
		
		request.putParam("page", String.valueOf(page));
		request.putParam("userId", UserManager.getCurrentUser().getUserId());
		
		HttpRequestManager.getInstance().sendRequest(request);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				getData(false);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		List<JokeContent> datas = adapter.getDatas();
		Intent intent = new Intent(context, JokeContentActivity.class);
		intent.putExtra("JokeContent", datas.get(position - 1));
		startActivity(intent);
	}
	
}
