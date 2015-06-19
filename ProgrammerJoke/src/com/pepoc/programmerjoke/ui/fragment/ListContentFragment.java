package com.pepoc.programmerjoke.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pepoc.programmerjoke.R;

/**
 * 段子列表页
 * @author yangchen
 *
 */
public class ListContentFragment extends BaseFragment {
	
	private View rootView;
	private ListView lvContentList;
	
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
	}
	
	@Override
	public void setListener() {
		super.setListener();
	}
}
