package com.wlqq.huodi.bean;

import java.io.Serializable;

/**
 * @author Tiger Tang Date: 11-12-30 Time: 下午9:33
 * @since 0.1.20
 */
public class LoginResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2401978494863534023L;

    private Session Session;
    private UserProfile profile;

    public Session getSession() {
        return Session;
    }

    public void setSession(Session session) {
        Session = session;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

}
