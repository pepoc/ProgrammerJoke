package com.pepoc.programmerjoke.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.pepoc.programmerjoke.PApplication;

public class Preference {
	
	private static final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
	private static final String PHONE_NUMBER = "phone_number";
	private static final String PASSWORD = "password";
	private static final String USER_ID = "user_id";
	private static final String IS_LOGIN = "is_login";

	public static void savePhoneNumber(String phoneNumber) {
		Editor editor = sp.edit();
		editor.putString(PHONE_NUMBER, phoneNumber);
		editor.commit();
	}
	
	public static String getPhoneNumber() {
		return sp.getString(PHONE_NUMBER, null);
	}
	
	public static void savePassword(String password) {
		Editor editor = sp.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	public static String getPassword() {
		return sp.getString(PASSWORD, null);
	}
	
	public static void saveUserId(String userId) {
		Editor editor = sp.edit();
		editor.putString(USER_ID, userId);
		editor.commit();
	}
	
	public static String getUserId() {
		return sp.getString(USER_ID, null);
	}
	
	public static void saveIsLogin(boolean isLogin) {
		Editor editor = sp.edit();
		editor.putBoolean(IS_LOGIN, isLogin);
		editor.commit();
	}
	
	public static boolean isLogin() {
		return sp.getBoolean(IS_LOGIN, false);
	}
	
}
