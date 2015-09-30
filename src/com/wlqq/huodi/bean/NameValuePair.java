package com.wlqq.huodi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tiger Tang
 *         Date: 12-2-8
 *         Time: 下午3:59
 * @since 0.1.20
 */
public class NameValuePair implements Parcelable {

    private long value;
    private String name;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Creator CREATOR = new Creator() {
        public NameValuePair createFromParcel(Parcel in) {
            return new NameValuePair(in);
        }

        public NameValuePair[] newArray(int size) {
            return new NameValuePair[size];
        }
    };

    public NameValuePair(Parcel in) {
        this.value = in.readLong();
        this.name = in.readString();
    }

    public NameValuePair(long value, String name) {
        this.value = value;
        this.name = name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(value);
        dest.writeString(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameValuePair)) return false;

        NameValuePair that = (NameValuePair) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("NameValuePair");
        sb.append("{value=").append(value);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
