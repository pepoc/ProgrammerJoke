package com.pepoc.programmerjoke.ui.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.ui.fragment.BaseFragment;
import com.pepoc.programmerjoke.ui.fragment.ListContentFragment;
import com.pepoc.programmerjoke.ui.fragment.PersonalCenterFragment;
import com.pepoc.programmerjoke.ui.fragment.WriteJokeFragment;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentTabHost tabHostMain;
	private Map<String, Class<? extends BaseFragment>> fragmentMaps = new HashMap<String, Class<? extends BaseFragment>>();

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
		
		fragmentMaps.put("tag1", ListContentFragment.class);
		fragmentMaps.put("tag2", WriteJokeFragment.class);
		fragmentMaps.put("tag3", PersonalCenterFragment.class);
		
		tabHostMain = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHostMain.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
		
		Set<String> keySet = fragmentMaps.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.ic_launcher);
			TabSpec tab = tabHostMain.newTabSpec(key).setIndicator(imageView);
			tabHostMain.addTab(tab, fragmentMaps.get(key), null);
		}
		
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
