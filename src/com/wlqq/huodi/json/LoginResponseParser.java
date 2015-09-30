package com.wlqq.huodi.json;


import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Tiger Tang
 *         Date: 11-12-30
 *         Time: 下午9:32
 * @since 0.1.20
 */
public final class LoginResponseParser implements Parser<LoginResponse> {

	private static final LoginResponseParser instance = new LoginResponseParser();

	private LoginResponseParser() {
	}

	public static LoginResponseParser getInstance() {
		return instance;
	}

	public LoginResponse parse(final String content) throws JSONException {
		final JSONObject jsonObject = new JSONObject(content);

		LoginResponse loginResponse = new LoginResponse();
		Session session = new Session();
		session.setId(jsonObject.optLong("id"));
		session.setToken(jsonObject.optString("token"));
		session.setStartTime(new Date(jsonObject.optLong("startTime")));
		session.setUserId(jsonObject.optLong("uid"));
		loginResponse.setSession(session);
		loginResponse.setProfile(UserProfileParser.getInstance().parse(jsonObject.getString("user")));

		return loginResponse;
	}
}
