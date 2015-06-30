package com.pepoc.programmerjoke.ui.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.fragment.BaseFragment;
import com.pepoc.programmerjoke.ui.fragment.ListContentFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;
import com.pepoc.programmerjoke.ui.fragment.WriteJokeFragment;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentTabHost tabHostMain;
	private Map<String, Class<? extends BaseFragment>> fragmentMaps = new HashMap<String, Class<? extends BaseFragment>>();
	private Map<String, Integer> titles = new HashMap<String, Integer>();
	private TextView tvMainFragmentTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		tvMainFragmentTitle = (TextView) findViewById(R.id.tv_main_fragment_title);
		
		titles.put("tag1", R.string.fragment_list_content_title);
		titles.put("tag2", R.string.fragment_write_joke_title);
		titles.put("tag3", R.string.fragment_personal_center);
		
		fragmentMaps.put("tag1", ListContentFragment.class);
		fragmentMaps.put("tag2", WriteJokeFragment.class);
		fragmentMaps.put("tag3", PersonalCenterFragment.class);
		
		tabHostMain = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHostMain.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
		
		for (String key : fragmentMaps.keySet()) {
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.ic_launcher);
			TabSpec tab = tabHostMain.newTabSpec(key).setIndicator(imageView);
			tabHostMain.addTab(tab, fragmentMaps.get(key), null);
		}
		
		tabHostMain.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				int strId = titles.get(tabId).intValue();
				tvMainFragmentTitle.setText(strId);
			}
		});
		
	}
	
	@Override
	public void setListener() {
		super.setListener();
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

}
