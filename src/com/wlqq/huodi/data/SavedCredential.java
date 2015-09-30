package com.wlqq.huodi.data;

import android.content.Context;
import android.content.SharedPreferences;


import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;

import org.apache.commons.lang.StringUtils;

/**
 * @author Tiger Tang
 *         Date: 12-1-3
 *         Time: 下午3:15
 * @since 0.1.20
 */
public class SavedCredential {

	public static enum AuthType {
		WULIUQQ,
		TENCENT
	}
 
	public static final String SAVED_USER_CREDENTIAL_KEY = "USER_CREDENTIAL";
	public static final String SAVED_PRINCIPAL_KEY = "USERNAME";
	public static final String SAVED_CREDENTIAL_KEY = "PASSWORD";
	public static final String SAVED_AUTHTYPE_KEY = "AUTHTYPE";

	public static final String OBSOLETE_SUC_KEY = "Session";
	public static final String OBSOLETE_SP_KEY = "userName";
	public static final String OBSOLETE_SC_KEY = "passWord";

	private static volatile SavedCredential instance;

	private String principal;
	private String credential;
	private AuthType authType;

	private SavedCredential() {
	}

	public static synchronized SavedCredential getInstance() {
		if (instance == null) {
			instance = new SavedCredential();

			migrateOldData();

			instance.setPrincipal(Preferences.getString(SAVED_PRINCIPAL_KEY, StringUtils.EMPTY), false);
			instance.setCredential(Preferences.getString(SAVED_CREDENTIAL_KEY, StringUtils.EMPTY), false);
			instance.setAuthType(AuthType.valueOf(Preferences.getString(SAVED_AUTHTYPE_KEY, AuthType.WULIUQQ.name())), false);
		}
		return instance;
	}

	private static void migrateOldData() {
		final SharedPreferences oldData = HuoDiApplication.getContext().getSharedPreferences(OBSOLETE_SUC_KEY, Context.MODE_PRIVATE);

		if (oldData.contains(OBSOLETE_SP_KEY)) {
			Preferences.set(SAVED_PRINCIPAL_KEY, oldData.getString(OBSOLETE_SP_KEY, StringUtils.EMPTY));
			Preferences.set(SAVED_CREDENTIAL_KEY, oldData.getString(OBSOLETE_SC_KEY, StringUtils.EMPTY));

			oldData.edit().remove(OBSOLETE_SP_KEY).remove(OBSOLETE_SC_KEY).commit();
		}
	}

	public boolean isNotNull() {
		return StringUtils.isNotBlank(principal) && StringUtils.isNotBlank(credential);
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(final String principal) {
		setPrincipal(principal, true);
	}

	private void setPrincipal(final String principal, final boolean persistent) {
		this.principal = principal;

		if (persistent) {
			Preferences.set(SAVED_PRINCIPAL_KEY, principal);
		}
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(final String credential) {
		setCredential(credential, true);
	}

	private void setCredential(final String credential, final boolean persistent) {
		this.credential = credential;

		if (persistent) {
			Preferences.set(SAVED_CREDENTIAL_KEY, credential);
		}
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		setAuthType(authType, true);
	}

	public void setAuthType(AuthType authType, final boolean persistent) {
		this.authType = authType;

		if (persistent) {
			Preferences.set(SAVED_AUTHTYPE_KEY, authType.name());
		}
	}
}
