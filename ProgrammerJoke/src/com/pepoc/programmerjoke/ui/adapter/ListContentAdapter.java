package com.pepoc.programmerjoke.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
			vh.tvCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
			vh.tvJokeId = (TextView) convertView.findViewById(R.id.tv_joke_id);
			vh.btnLikeJoke = (TextView) convertView.findViewById(R.id.btn_like_joke);
			vh.btnCollectJoke = (TextView) convertView.findViewById(R.id.btn_collect_joke);
			vh.tvLikeCount = (TextView) convertView.findViewById(R.id.tv_like_count);
			vh.tvCollectCount = (TextView) convertView.findViewById(R.id.tv_collect_count);
			convertView.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) convertView.getTag();
		JokeContent jokeContent = datas.get(position);
		vh.tvUserName.setText(jokeContent.getUserNickName());
		vh.tvContent.setText(jokeContent.getContent());
		vh.tvCreateTime.setText(jokeContent.getCreateTime());
		vh.tvJokeId.setText(jokeContent.getJokeId());
		vh.btnLikeJoke.setText("喜欢:" + jokeContent.getIslike());
		vh.tvLikeCount.setText(jokeContent.getLikeCount());
		vh.btnCollectJoke.setText("收藏:" + jokeContent.getIscollect());
		vh.tvCollectCount.setText(jokeContent.getCollectCount());
		if (TextUtils.isEmpty(jokeContent.getUserAvatar())) {
			vh.ivUserAvatar.setImageResource(R.drawable.icon);
		} else {
			PImageLoader.getInstance().displayImage(jokeContent.getUserAvatar(), vh.ivUserAvatar);
		}
		return convertView;
	}
	
	class ViewHolder {
		ImageView ivUserAvatar;
		TextView tvUserName;
		TextView tvContent;
		TextView tvCreateTime;
		TextView tvJokeId;
		TextView btnLikeJoke;
		TextView btnCollectJoke;
		TextView tvLikeCount;
		TextView tvCollectCount;
	}

}
