package com.pepoc.programmerjoke.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pepoc.programmerjoke.log.Log;
import com.pepoc.programmerjoke.log.LogFactory;

public class BaseFragment extends Fragment {
	
	public final Log log = LogFactory.getLog(this.getClass());
	public Context context;
	private View rootView;
	private int layoutResID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(layoutResID, null);

			init();
			setListener();
		}
		
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (null != parent) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	
	public void setContentView(int layoutResID) {
		this.layoutResID = layoutResID;
	}
	
	public View findViewById(int id) {
		return rootView.findViewById(id);
	}
	
	public void init() {

	}
	
	public void setListener() {

	}
}
