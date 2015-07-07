package com.pepoc.programmerjoke.ui.fragment;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.constants.Constant;
import com.pepoc.programmerjoke.net.PImageLoader;
import com.pepoc.programmerjoke.net.http.HttpRequestManager;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;
import com.pepoc.programmerjoke.net.http.request.RequestUpToken;
import com.pepoc.programmerjoke.net.http.request.RequestUpdateUserInfo;
import com.pepoc.programmerjoke.observer.LoginObservable;
import com.pepoc.programmerjoke.ui.activity.ClipImageActivity;
import com.pepoc.programmerjoke.ui.activity.LoginActivity;
import com.pepoc.programmerjoke.ui.activity.RegisterActivity;
import com.pepoc.programmerjoke.user.UserInfo;
import com.pepoc.programmerjoke.user.UserManager;
import com.pepoc.programmerjoke.utils.Preference;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * 个人中心
 * @author yangchen
 *
 */
public class PersonalCenterFragment extends BaseFragment implements OnClickListener, Observer {
	
	private View llPersonalInfo;
	private View llLoginOrRegister;
	private Button btnLogin;
	private Button btnRegister;
	private ImageView ivAvatar;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String picturePath;
	private String key;
	private String uploadToken;
	private Button btnLogout;
	private TextView tvNickName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal_center);
		LoginObservable.getInstance().addObserver(this);
		
	}
	
	@Override
	public void init() {
		super.init();
		
		llPersonalInfo = findViewById(R.id.ll_personal_info);
		llLoginOrRegister = findViewById(R.id.ll_login_or_register);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnRegister = (Button) findViewById(R.id.btn_register);
		ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
		tvNickName = (TextView) findViewById(R.id.tv_nick_name);
		btnLogout = (Button) findViewById(R.id.btn_logout);
		
		// 判断一下登录状态
		if (Preference.isLogin()) {
			setLoginStatus(true);
		}
	}
	
	@Override
	public void setListener() {
		super.setListener();
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		ivAvatar.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			Intent loginIntent = new Intent(context, LoginActivity.class);
			startActivity(loginIntent);
			break;
		case R.id.btn_register:
			Intent registerIntent = new Intent(context, RegisterActivity.class);
			startActivity(registerIntent);
			break;
		case R.id.iv_avatar:
			getHeaderFromGallery();
			break;
		case R.id.btn_logout:
			setLoginStatus(false);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			String[] filePathColumns = {MediaStore.Images.Media.DATA};
			Cursor cursor = context.getContentResolver().query(uri, filePathColumns, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
				picturePath = cursor.getString(columnIndex);
				log.info("Picture Path === " + picturePath);
				
				Intent intent = new Intent(context, ClipImageActivity.class);
				intent.putExtra("picturePath", picturePath);
				startActivityForResult(intent, 11);
			}
			cursor.close();
		} else if (resultCode == Constant.RESULT_OK) {
			picturePath = data.getStringExtra("filePath");
			if (!TextUtils.isEmpty(picturePath)) {
				upLoadAvatar();
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		setLoginStatus(true);
	}
	
	/**
	 * 登录状态 处理
	 * @param loginStatus true:为登录成功       false:为未登录
	 */
	private void setLoginStatus(boolean loginStatus) {
		if (loginStatus) {
			llPersonalInfo.setVisibility(View.VISIBLE);
			llLoginOrRegister.setVisibility(View.GONE);
			UserInfo currentUser = UserManager.getCurrentUser();
			tvNickName.setText(currentUser.getNickName());
			if (TextUtils.isEmpty(currentUser.getAvatar())) {
				ivAvatar.setImageResource(R.drawable.icon);
			} else {
				PImageLoader.getInstance().displayImage(currentUser.getAvatar(), ivAvatar);
			}
		} else {
			llPersonalInfo.setVisibility(View.GONE);
			llLoginOrRegister.setVisibility(View.VISIBLE);
			
			UserManager.setCurrentUser(null);
			Preference.saveIsLogin(false);
			Preference.saveAccountNumber(null);
			Preference.savePassword(null);
		}
	}
	
	/**
	 * 上传头像
	 */
	private void upLoadAvatar() {
		RequestUpToken requestUpToken = new RequestUpToken(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				try {
					JSONObject obj = new JSONObject((String)result);
					String status = obj.getString("status");
					if ("1".equals(status)) {
						uploadToken = obj.getString("upToken");
					}
				} catch (JSONException e) {
					log.error("get uptoken", e);
				}
				
				// 七牛上传
				UploadManager uploadManager = new UploadManager();
				uploadManager.put(picturePath, key, uploadToken, new UpCompletionHandler() {
					
					@Override
					public void complete(String key, ResponseInfo info, JSONObject response) {
						if (info.isOK()) {
							Log.i("qiniu", "=== upload success ===");
							Toast.makeText(context, "upload success", Toast.LENGTH_SHORT).show();
							uploadAvatarKey();
						} else {
							Log.i("qiniu", "fail");
							Toast.makeText(context, "upload fail", Toast.LENGTH_SHORT).show();
						}
					}
				}, null);
			}
		});
		
		key = "pj_avatar_" + System.currentTimeMillis();
		requestUpToken.putParam("key", key);
		
		HttpRequestManager.getInstance().sendRequest(requestUpToken);
	}
	
	/**
	 * 上传头像
	 */
	private void uploadAvatarKey() {
		RequestUpdateUserInfo requestUpdateUserInfo = new RequestUpdateUserInfo(new OnHttpResponseListener() {
			
			@Override
			public void onHttpResponse(Object result) {
				PImageLoader.getInstance().displayImage(key, ivAvatar);
			}
		});
		
		requestUpdateUserInfo.putParam("userId", UserManager.getCurrentUser().getUserId());
		requestUpdateUserInfo.putParam("avatar", key);
		
		HttpRequestManager.getInstance().sendRequest(requestUpdateUserInfo);
	}
	
	/**
	 * 打开相册
	 */
	private void getHeaderFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
		startActivityForResult(intent, 99);
	}
	
}
