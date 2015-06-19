package com.pepoc.programmerjoke.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListContentAdapter extends BaseAdapter {
	
	private Context context;
	private List<String> datas = new ArrayList<String>();
	
	public ListContentAdapter(Context context) {
		this.context = context;
	}

	public List<String> getDatas() {
		return datas;
	}

	public void setDatas(List<String> datas) {
		this.datas.addAll(datas);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			TextView textView = new TextView(context);
			textView.setTextSize(20);
			convertView = textView;
		}
		((TextView)convertView).setText(datas.get(position));
		return convertView;
	}

}
