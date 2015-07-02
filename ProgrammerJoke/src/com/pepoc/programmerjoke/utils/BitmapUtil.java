package com.pepoc.programmerjoke.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapUtil {

	/**
	 * 获取图片的旋转角度
	 * @param path
	 * @return
	 */
	public static int readBitmapDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation  = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	/**
	 * 旋转图片
	 * @param degree
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
		//旋转图片 动作  
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);  
		// 创建新的图片  
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
		bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
		return resizedBitmap;  
	}
	
	/**
	 * 创建一个缩放的图片
	 * 
	 * @param path
	 *            图片地址
	 * @param w
	 *            图片宽度
	 * @param h
	 *            图片高度
	 * @return 缩放后的图片
	 */
	public static Bitmap createBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;// 获取图片的原始宽度
			int srcHeight = opts.outHeight;// 获取图片原始高度
//			int destWidth = 0;
//			int destHeight = 0;
			int reqHeight = 1280;// 这里阀值设置高度为1280
			int reqWidth = 720;// 这里阀值设置宽度为720
			// 缩放的比例
			double ratio = 1.0;
			if (srcWidth < reqWidth || srcHeight < reqHeight) {
				ratio = 1.0;
//				destWidth = srcWidth;
//				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
				ratio = (double) srcWidth / w;
//				destWidth = w;
//				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
//				destHeight = h;
//				destWidth = (int) (srcWidth / ratio);
			}
			// inJustDecodeBounds设为false表示把图片读进内存中
			opts.inJustDecodeBounds = false;
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			opts.inSampleSize = (int) ratio;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
//			newOpts.outHeight = destHeight;
//			newOpts.outWidth = destWidth;
			// 获取缩放后图片
			return BitmapFactory.decodeFile(path, opts);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
