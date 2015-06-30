package com.pepoc.programmerjoke.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.pepoc.programmerjoke.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_content, null);
			ViewHolder vh = new ViewHolder();
			vh.ivUserAvatar = (ImageView) convertView.findViewById(R.id.iv_user_avatar);
			vh.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
			vh.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) convertView.getTag();
		vh.tvContent.setText(datas.get(position));
		return convertView;
	}
	
	class ViewHolder {
		ImageView ivUserAvatar;
		TextView tvUserName;
		TextView tvContent;
	}

}
