package com.pepoc.programmerjoke.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestGetJokes;
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
	private int page = 1;
	
	/** 是否还有更多数据 */
	private boolean isHasMoreData = true;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1000:
				mPullRefreshListView.onRefreshComplete();
				break;

			default:
				break;
			}
		}
	};
	
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
		
		getData(true);
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
		intent.putExtra("JokeContent", datas.get(position - 1));
		startActivity(intent);
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
				Toast.makeText(context, "没有更多的数据了", Toast.LENGTH_SHORT).show();
				handler.sendEmptyMessage(1000);
				return ;
			}
			page++;
		}
		
		RequestGetJokes request = new RequestGetJokes(new OnHttpResponseListener() {
			
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
				mPullRefreshListView.onRefreshComplete();
			}
		});
		
		request.putParam("page", String.valueOf(page));
		
		HttpRequestManager.getInstance().sendRequest(request);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getData(true);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getData(false);
	}

}
