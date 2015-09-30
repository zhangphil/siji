package com.wlqq.huodi.bean;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7457968450162187258L;

    private long id; // Session ID
    private String token; // Session Token
    private Date startTime; // Start Time
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;

        Session session = (Session) o;

        if (id != session.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Session");
        sb.append("{id=").append(id);
        sb.append(", token='").append(token).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
