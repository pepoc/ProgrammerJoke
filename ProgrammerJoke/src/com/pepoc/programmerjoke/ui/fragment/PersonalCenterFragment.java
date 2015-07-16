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
import com.pepoc.programmerjoke.ui.activity.CollectedJokeActivity;
import com.pepoc.programmerjoke.ui.activity.LoginActivity;
import com.pepoc.programmerjoke.ui.activity.PublishedJokeActivity;
import com.pepoc.programmerjoke.ui.activity.RegisterActivity;
import com.pepoc.programmerjoke.ui.activity.Setting;
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
	
	private ImageView ivAvatar;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String picturePath;
	private String key;
	private String uploadToken;
	private TextView tvNickName;
	private View llCollected;
	private View llPublished;
	private View llSetting;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal_center);
		LoginObservable.getInstance().addObserver(this);
		
	}
	
	@Override
	public void init() {
		super.init();
		ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
		tvNickName = (TextView) findViewById(R.id.tv_nick_name);
		llCollected = findViewById(R.id.ll_collected);
		llPublished = findViewById(R.id.ll_published);
		llSetting = findViewById(R.id.ll_setting);
		
		setLoginStatus(Preference.isLogin());
	}
	
	@Override
	public void setListener() {
		super.setListener();
		ivAvatar.setOnClickListener(this);
		llCollected.setOnClickListener(this);
		llPublished.setOnClickListener(this);
		llSetting.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (!Preference.isLogin()) {
			Intent loginIntent = new Intent(context, LoginActivity.class);
			startActivity(loginIntent);
			return ;
		}
		switch (v.getId()) {
		case R.id.iv_avatar:
			getHeaderFromGallery();
			break;
		case R.id.ll_collected:
			Intent collectedIntent = new Intent(context, CollectedJokeActivity.class);
			startActivity(collectedIntent);
			break;
		case R.id.ll_published:
			Intent publishedIntent = new Intent(context, PublishedJokeActivity.class);
			startActivity(publishedIntent);
			break;
		case R.id.ll_setting:
			Intent settingIntent = new Intent(context, Setting.class);
			startActivityForResult(settingIntent, Constant.REQUEST_CODE);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
		case Activity.RESULT_OK:
			Uri uri = data.getData();
			String[] filePathColumns = {MediaStore.Images.Media.DATA};
			Cursor cursor = context.getContentResolver().query(uri, filePathColumns, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
				picturePath = cursor.getString(columnIndex);
				log.i("Picture Path === " + picturePath);
				
				Intent intent = new Intent(context, ClipImageActivity.class);
				intent.putExtra("picturePath", picturePath);
				startActivityForResult(intent, 11);
			}
			cursor.close();
			break;
		case Constant.RESULT_OK:
			picturePath = data.getStringExtra("filePath");
			if (!TextUtils.isEmpty(picturePath)) {
				upLoadAvatar();
			}
			break;
		case Constant.RESULT_LOGOUT_OK:
			setLoginStatus(false);
			break;

		default:
			break;
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
		UserInfo currentUser = UserManager.getCurrentUser();
		if (loginStatus && currentUser != null) {
			tvNickName.setText(currentUser.getNickName());
			if (TextUtils.isEmpty(currentUser.getAvatar())) {
				ivAvatar.setImageResource(R.drawable.icon);
			} else {
				PImageLoader.getInstance().displayImage(currentUser.getAvatar(), ivAvatar);
			}
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
					log.e("get uptoken", e);
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
