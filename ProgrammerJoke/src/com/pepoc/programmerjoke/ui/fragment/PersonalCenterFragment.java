package com.pepoc.programmerjoke.ui.fragment;

import com.pepoc.programmerjoke.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PersonalCenterFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.info("--------------- onCreate --------------");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		log.info("--------------- onCreateView --------------");
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.ic_launcher);
		return imageView;
	}
}
