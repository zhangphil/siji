package com.wlqq.huodi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Tiger Tang
 *         Date: 12-3-9
 *         Time: 下午1:00
 * @since 0.1.20
 */
public class AddressComponent implements Parcelable, Serializable {

    private double latitude;
    private double longitude;

    private String name;

    private String formattedAddress;
    private String province;
    private String city;
    private String county;
    private String street;

    public AddressComponent() {
    }

    public AddressComponent(double latitude, double longitude, String city, String formattedAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.formattedAddress = formattedAddress;
    }

    public AddressComponent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressComponent(String formattedAddress, String province, String city, String county, String street) {
        this.formattedAddress = formattedAddress;
        this.province = province;
        this.city = city;
        this.county = county;
        this.street = street;
    }

    public AddressComponent(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();

        this.name = in.readString();

        this.formattedAddress = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.county = in.readString();
        this.street = in.readString();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getILatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public  double getILongitude() {
        return   longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);

        dest.writeString(name);

        dest.writeString(formattedAddress);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(street);
    }

    public static final Creator CREATOR = new Creator() {
        public AddressComponent createFromParcel(Parcel in) {
            return new AddressComponent(in);
        }

        public AddressComponent[] newArray(int size) {
            return new AddressComponent[size];
        }
    };
}
