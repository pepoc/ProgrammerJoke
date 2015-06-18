package com.pepoc.programmerjoke.ui.fragment;

import com.pepoc.programmerjoke.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ListContentFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.info("--------------- onCreate --------------");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info("--------------- onCreateView -------------- container = " + container);
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.bg_splash);
		return imageView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		log.info("--------------- onActivityCreated --------------");
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStop() {
		log.info("--------------- onStop --------------");
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		log.info("--------------- onDestroyView --------------");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		log.info("--------------- onDestroy --------------");
		super.onDestroy();
	}
}
