package com.pepoc.programmerjoke.user;

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
		return currentUser;
	}

	public static void setCurrentUser(UserInfo currentUser) {
		UserManager.currentUser = currentUser;
	}
	
}
