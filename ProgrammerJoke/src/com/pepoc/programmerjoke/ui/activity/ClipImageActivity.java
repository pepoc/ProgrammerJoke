package com.pepoc.programmerjoke.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.utils.BitmapUtil;

public class ClipImageActivity extends BaseActivity {

	private String picturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clip_image);
		
		Intent intent = getIntent();
		picturePath = intent.getStringExtra("picturePath");
		
		init();
		setListener();
	}
	
	@Override
	public void init() {
		super.init();
		
		
		int degree = BitmapUtil.readBitmapDegree(picturePath);
		Bitmap bm = BitmapUtil.createBitmap(picturePath, mScreenWidth, mScreenHeight);
		if (0 != degree) {
			bm = BitmapUtil.rotateBitmap(degree, bm);
		}
	}
	
	@Override
	public void setListener() {
		super.setListener();
	}
	
}
