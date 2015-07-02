package com.pepoc.programmerjoke.net;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pepoc.programmerjoke.Config;
import com.pepoc.programmerjoke.utils.BitmapUtil;

public class PImageLoader {

	private static PImageLoader instance = null;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	private PImageLoader() {
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true).build();
	}
	
	public static PImageLoader getInstance() {
		if (null == instance) {
			instance = new PImageLoader();
		}
		return instance;
	}
	
	public void displayImage(String uri, ImageView imageView) {
		if (!TextUtils.isEmpty(uri)) {
			imageLoader.displayImage(Config.IMAGE_HOST + uri + Config.IMAGE_SIZE, imageView, options);
		}
	}
	
	class PImageLoadingListener extends SimpleImageLoadingListener {
		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageImage = (ImageView) view;
				Bitmap roundBitmap = BitmapUtil.toRoundBitmap(loadedImage);
				if (null != roundBitmap) {
					imageImage.setImageBitmap(roundBitmap);
				}
			}
			super.onLoadingComplete(imageUri, view, loadedImage);
		}
	}
}
