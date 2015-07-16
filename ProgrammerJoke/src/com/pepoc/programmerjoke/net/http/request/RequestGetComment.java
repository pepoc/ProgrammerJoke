package com.pepoc.programmerjoke.net.http.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.data.bean.JokeComment;
import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestGetComment extends HttpRequest {
	
	public RequestGetComment(OnHttpResponseListener onHttpResponseListener) {
		this.URL = "getcomment.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		List<JokeComment> jokeComments = new ArrayList<JokeComment>();
		try {
			JSONObject obj = new JSONObject(result);
			String status = obj.getString("status");
			if ("1".equals(status)) {
				JSONArray commentListArr = obj.getJSONArray("commentList");
				for (int i = 0; i < commentListArr.length(); i++) {
					JSONObject commentListObj = commentListArr.getJSONObject(i);
					JokeComment jokeComment = new JokeComment();
					jokeComment.setCommentId(commentListObj.getString("commentId"));
					jokeComment.setContent(commentListObj.getString("content"));
					jokeComment.setCreateTime(commentListObj.getString("createTime"));
					jokeComment.setLikeNumber(commentListObj.getString("likeNum"));
					jokeComment.setUserId(commentListObj.getString("userId"));
					jokeComment.setUserNickName(commentListObj.getString("nickName"));
					jokeComment.setUserAvatar(commentListObj.getString("avatar"));
					
					jokeComments.add(jokeComment);
				}
			}
		} catch (JSONException e) {
			log.e("parseResponseResult", e);
		}
		return jokeComments;
	}

}
