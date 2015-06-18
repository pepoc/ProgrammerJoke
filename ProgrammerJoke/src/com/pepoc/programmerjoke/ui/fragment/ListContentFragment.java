package com.pepoc.programmerjoke.ui.fragment;

import com.pepoc.programmerjoke.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 显示段子列表页
 * @author yangchen
 *
 */
public class ListContentFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.bg_splash);
		return imageView;
	}
}
