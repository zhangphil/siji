package hcb.tc.sj.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Account {
	private static final String NAME = "huochebang tongcheng account";

	public static final String LOGIN_STATUS = "login status";

	public static interface USER {
		public static final String NICKNAME = "nickname";
		public static final String PASSWORD = "password";
		public static final String PASSWORD_CONFIRMATION = "password_confirmation";
		public static final String EMAIL = "email";
		public static final String PHONE_NO = "phone_no";
		public static final String ACCESS_TOKEN = "access_token";
	}

	public static boolean getUserLoginStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences(Account.NAME, Context.MODE_PRIVATE);
		boolean login = sp.getBoolean(LOGIN_STATUS, false);
		return login;
	}

	public static void setUserLoginStatus(Context context, boolean status) {
		SharedPreferences sp = context.getSharedPreferences(Account.NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(LOGIN_STATUS, status);
		editor.commit();
	}
}