package com.pepoc.programmerjoke.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.data.bean.JokeComment;
import com.pepoc.programmerjoke.data.bean.JokeContent;
import com.pepoc.programmerjoke.net.PImageLoader;
import com.pepoc.programmerjoke.ui.adapter.ListContentAdapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JokeContentAdapter extends BaseAdapter {
	
	private Context context;
	private List<JokeComment> jokeComments = new ArrayList<JokeComment>();

	public JokeContentAdapter(Context context) {
		this.context = context;
	}
	
	public List<JokeComment> getJokeComments() {
		return jokeComments;
	}

	public void setJokeComments(List<JokeComment> jokeComments) {
		this.jokeComments = jokeComments;
	}

	@Override
	public int getCount() {
		return jokeComments.size();
	}

	@Override
	public Object getItem(int position) {
		return jokeComments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.item_joke_comment, null);
			ViewHolder vh = new ViewHolder();
			vh.ivUserAvatar = (ImageView) convertView.findViewById(R.id.iv_user_avatar);
			vh.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
			vh.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
			convertView.setTag(vh);
		}
		
		ViewHolder vh = (ViewHolder) convertView.getTag();
		JokeComment jokeComment = jokeComments.get(position);
		vh.tvComment.setText(jokeComment.getContent());
		PImageLoader.getInstance().displayImage(jokeComment.getUserAvatar(), vh.ivUserAvatar);
		return convertView;
	}
	
	class ViewHolder {
		ImageView ivUserAvatar;
		TextView tvUserName;
		TextView tvComment;
	}

}
