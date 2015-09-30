package com.wlqq.huodi.bean;

import java.io.Serializable;

/**
 * @author Tiger Tang
 *         Date: 12-1-15
 *         Time: 下午2:49
 * @since 0.1.20
 */
public class CSSite implements Serializable {

    private long id;
    private long pid;
    private long cid;
    private String name;
    private String address;
    private String contactor;
    private String tel1;
    private String tel2;
    private String tel3;
    private String mobile1;
    private String mobile2;
    private String mobile3;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CSSite)) return false;

        CSSite csSite = (CSSite) o;

        return id == csSite.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CSSite");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", contactor='").append(contactor).append('\'');
        sb.append(", tel1='").append(tel1).append('\'');
        sb.append(", tel2='").append(tel2).append('\'');
        sb.append(", tel3='").append(tel3).append('\'');
        sb.append(", mobile1='").append(mobile1).append('\'');
        sb.append(", mobile2='").append(mobile2).append('\'');
        sb.append(", mobile3='").append(mobile3).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
