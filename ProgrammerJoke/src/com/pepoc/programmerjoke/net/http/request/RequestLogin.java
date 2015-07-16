package com.pepoc.programmerjoke.net.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.user.UserInfo;
import com.pepoc.programmerjoke.user.UserManager;

public class RequestLogin extends HttpRequest {
	
	public RequestLogin(OnHttpResponseListener onHttpResponseListener) {
		this.requestMethod = METHOD_POST;
		this.URL = "login.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		try {
			JSONObject obj = new JSONObject(result);
			String status = obj.getString("status");
			if ("1".equals(status)) {
				JSONObject userInfoObj = obj.getJSONObject("userInfo");
				UserInfo userInfo = new UserInfo();
				userInfo.setAccountNumber(params.get("accountNumber"));
				userInfo.setPassword(params.get("password"));
				userInfo.setUserId(userInfoObj.getString("userId"));
				userInfo.setNickName(userInfoObj.getString("nickName"));
				userInfo.setSex(userInfoObj.getString("sex"));
				userInfo.setAge(userInfoObj.getString("age"));
				userInfo.setAvatar(userInfoObj.getString("avatar"));
				userInfo.setCity(userInfoObj.getString("city"));
				userInfo.setRegisterTime(userInfoObj.getString("registerTime"));
				userInfo.setLoginTime(userInfoObj.getString("loginTime"));
				userInfo.setLoginType(userInfoObj.getString("loginType"));
				
				UserManager.setCurrentUser(userInfo);
				
				return true;
			}
		} catch (JSONException e) {
			log.e("parseResponseResult", e);
		}
		return false;
	}

}
