package com.pepoc.programmerjoke.ui.fragment;

import com.pepoc.programmerjoke.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 个人中心
 * @author yangchen
 *
 */
public class PersonalCenterFragment extends BaseFragment {
	
	private View rootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_personal_center, null);
		}
		
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (null != parent) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	
}
