package com.wlqq.huodi.json;


import com.wlqq.huodi.bean.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Tiger Tang
 *         Date: 11-12-30
 *         Time: 下午9:30
 * @since 0.1.20
 */
public final class SessionParser implements Parser<Session> {

    private static final SessionParser instance = new SessionParser();

    private SessionParser() {
    }

    public static SessionParser getInstance() {
        return instance;
    }

    public Session parse(final String content) throws JSONException {
        final JSONObject jsonObject = new JSONObject(content);

        Session session = new Session();

        session.setId(jsonObject.optLong("id"));
        session.setToken(jsonObject.optString("token"));
        session.setStartTime(new Date(jsonObject.optLong("startTime")));
        session.setUserId(jsonObject.optLong("uid"));

        return session;
    }
}
