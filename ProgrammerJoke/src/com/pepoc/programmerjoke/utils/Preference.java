package com.pepoc.programmerjoke.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.pepoc.programmerjoke.PApplication;

public class Preference {
	
	private static final String PHONE_NUMBER = "phone_number";
	private static final String PASSWORD = "password";
	private static final String IS_LOGIN = "is_login";

	public static void savePhoneNumber(String phoneNumber) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		Editor editor = sp.edit();
		editor.putString(PHONE_NUMBER, phoneNumber);
		editor.commit();
	}
	
	public static String getPhoneNumber() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		return sp.getString(PHONE_NUMBER, null);
	}
	
	public static void savePassword(String password) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		Editor editor = sp.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	public static String getPassword() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		return sp.getString(PASSWORD, null);
	}
	
	public static void saveIsLogin(boolean isLogin) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		Editor editor = sp.edit();
		editor.putBoolean(IS_LOGIN, isLogin);
		editor.commit();
	}
	
	public static boolean isLogin() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PApplication.getInstance());
		return sp.getBoolean(IS_LOGIN, false);
	}
	
}
