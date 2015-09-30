package com.wlqq.huodi.bean;

import android.graphics.Bitmap;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * @author xlw
 *         Date: 12-6-13
 */
public class SearchPoi implements Serializable {
    private String address;
    private String name;
    private String distance;
    private Bitmap bitmap;
    private LatLng geoPoint;

    public LatLng getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(LatLng geoPoint) {
        this.geoPoint = geoPoint;
    }

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
