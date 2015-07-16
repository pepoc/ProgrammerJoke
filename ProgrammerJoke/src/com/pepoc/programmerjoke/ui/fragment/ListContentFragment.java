package com.pepoc.programmerjoke.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.manager.WXManager;
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
public class ListContentFragment extends BaseFragment implements OnClickListener, OnItemClickListener, OnItemLongClickListener, OnRefreshListener2<ListView>, OnScrollListener {
	
	private PullToRefreshListView mPullRefreshListView;
	private ListView lvContentList;
	private ListContentAdapter adapter;
	private int page = 1;
	private View footerView;
	
	/** 是否还有更多数据 */
	private boolean isHasMoreData = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list_content);
	}
	
	@Override
	public void init() {
		super.init();
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_content_list_refresh);
		mPullRefreshListView.setPullToRefreshOverScrollEnabled(false);
		lvContentList = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setOnRefreshListener(this);
		adapter = new ListContentAdapter(context);
		lvContentList.setAdapter(adapter);
		
		footerView = View.inflate(context, R.layout.footer_view, null);
		footerView.setVisibility(View.GONE);
		View ivProgressLoad = footerView.findViewById(R.id.iv_progress_load);
		ivProgressLoad.setOnClickListener(this);
		lvContentList.addFooterView(footerView, null, false);
		
		getData(true);
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
		lvContentList.setOnItemClickListener(this);
		footerView.setOnClickListener(this);
		
		lvContentList.setOnScrollListener(this);
		lvContentList.setOnItemLongClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_progress_load:
			Toast.makeText(context, "lalala", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		List<JokeContent> datas = adapter.getDatas();
		Intent intent = new Intent(context, JokeContentActivity.class);
		intent.putExtra("JokeContent", datas.get(position - 1));
		startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		List<JokeContent> datas = adapter.getDatas();
		JokeContent jokeContent = datas.get(position - 1);
		String url = "pepoc.com/programmerjoke/singlejoke.php?joke_id=" + jokeContent.getJokeId();
		WXManager.shareUrlToWeChat(context, url, jokeContent.getContent(), jokeContent.getContent(), WXManager.SHARE_MOMENTS);
		return true;
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
				footerView.setVisibility(View.VISIBLE);
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

}
