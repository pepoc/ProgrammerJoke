package com.pepoc.programmerjoke.ui.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pepoc.programmerjoke.R;
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
	
	private View rootView;
	private ListView lvContentList;
	private ListContentAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_list_content, null);
			
			init();
			setListener();
			
//			getData();
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
		
		lvContentList = (ListView) rootView.findViewById(R.id.lv_content_list);
		adapter = new ListContentAdapter(context);
		lvContentList.setAdapter(adapter);
	}
	
	@Override
	public void setListener() {
		super.setListener();
	}
	
	private void getData() {
		
		RequestListContent request = new RequestListContent(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				adapter.setDatas((List<String>) result);
				adapter.notifyDataSetChanged();
			}
		});
		
		HttpRequestManager.getInstance().sendRequest(request);
	}
}
