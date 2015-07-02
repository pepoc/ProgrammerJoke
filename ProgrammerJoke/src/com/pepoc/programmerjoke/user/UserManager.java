package com.pepoc.programmerjoke.user;

import com.pepoc.programmerjoke.utils.Preference;

/**
 * 
 * @author yangchen
 *
 */
public class UserManager {

	private static UserManager instance = null;
	private static UserInfo currentUser = null;
	
	private UserManager() {
		
	}
	
	public static UserManager getInstance() {
		if (null == instance) {
			instance = new UserManager();
		}
		return instance;
	}

	public static UserInfo getCurrentUser() {
		if (currentUser == null) {
			currentUser = new UserInfo();
			currentUser.setUserId(Preference.getUserId());
		}
		return currentUser;
	}

	public static void setCurrentUser(UserInfo currentUser) {
		UserManager.currentUser = currentUser;
	}
	
}
