package com.wlqq.huodi.bean;

import java.io.Serializable;

/**
 * @author xlw
 *         Date: 13-6-20
 */
public class ParkPoiContent implements Serializable {
    private String address;
    private String name;

    private String telephone;
    private String price;
    private String createTime;

    private int distance;

    private double latitude;
    private double longitude;

    private int totalBerthCount;
    private int leftBerthCount;

    private int totalRoomCount;
    private int leftRoomCount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTotalBerthCount() {
        return totalBerthCount;
    }

    public void setTotalBerthCount(int totalBerthCount) {
        this.totalBerthCount = totalBerthCount;
    }

    public int getLeftBerthCount() {
        return leftBerthCount;
    }

    public void setLeftBerthCount(int leftBerthCount) {
        this.leftBerthCount = leftBerthCount;
    }

    public int getTotalRoomCount() {
        return totalRoomCount;
    }

    public void setTotalRoomCount(int totalRoomCount) {
        this.totalRoomCount = totalRoomCount;
    }

    public int getLeftRoomCount() {
        return leftRoomCount;
    }

    public void setLeftRoomCount(int leftRoomCount) {
        this.leftRoomCount = leftRoomCount;
    }
}
