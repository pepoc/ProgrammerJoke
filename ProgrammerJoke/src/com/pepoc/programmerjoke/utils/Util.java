package com.pepoc.programmerjoke.utils;

import java.io.File;

import android.os.Environment;

public class Util {

	public static String getBaseFilePath() {
		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ProgrammerJoke";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		} 
		return file.getAbsolutePath();
	}
}
