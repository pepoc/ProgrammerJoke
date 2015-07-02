package com.pepoc.programmerjoke.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class JokeContent implements Parcelable {

	private String jokeId;
	private String content;
	private String userId;
	private String userNickName;
	private String userAvatar;
	
	public String getJokeId() {
		return jokeId;
	}
	public void setJokeId(String jokeId) {
		this.jokeId = jokeId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(jokeId);
		dest.writeString(content);
		dest.writeString(userId);
		dest.writeString(userNickName);
		dest.writeString(userAvatar);
	}
	
	public static final Parcelable.Creator<JokeContent> CREATOR = new Creator<JokeContent>() {

		@Override
		public JokeContent createFromParcel(Parcel source) {
			JokeContent jokeContent = new JokeContent();
			jokeContent.jokeId = source.readString();
			jokeContent.content = source.readString();
			jokeContent.userId = source.readString();
			jokeContent.userNickName = source.readString();
			jokeContent.userAvatar = source.readString();
			return jokeContent;
		}

		@Override
		public JokeContent[] newArray(int size) {
			return new JokeContent[size];
		}
	};
	
}
