package com.pepoc.programmerjoke.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.PImageLoader;

public class ListContentAdapter extends BaseAdapter {
	
	private Context context;
	private List<JokeContent> datas = new ArrayList<JokeContent>();
	
	public ListContentAdapter(Context context) {
		this.context = context;
	}

	public List<JokeContent> getDatas() {
		return datas;
	}

	public void setDatas(List<JokeContent> datas) {
		this.datas = datas;
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
			vh.tvCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
			convertView.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) convertView.getTag();
		JokeContent jokeContent = datas.get(position);
		vh.tvContent.setText(jokeContent.getContent());
		vh.tvCreateTime.setText(jokeContent.getCreateTime());
		PImageLoader.getInstance().displayImage(jokeContent.getUserAvatar(), vh.ivUserAvatar);
		return convertView;
	}
	
	class ViewHolder {
		ImageView ivUserAvatar;
		TextView tvUserName;
		TextView tvContent;
		TextView tvCreateTime;
	}

}
