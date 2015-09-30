package com.wlqq.huodi.bean;

import java.io.Serializable;

/**
 * @author Tiger Tang
 *         Date: 12-1-6
 *         Time: 下午4:20
 * @since 0.1.20
 */
public class Region implements Serializable {

    private long id;
    private String name;
    private long parent;
    private int level;
    private int lat;
    private int lng;

    public Region(long id, String name) {
        this.id = id;
        this.name = name;

        if (id > 100) {
            this.parent = id / 100;
            this.level = id > 10000 ? 2 : 1;
        }
    }

    public Region(long id, String name, int lat, int lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;

        if (id > 100) {
            this.parent = id / 100;
            this.level = id > 10000 ? 2 : 1;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParent() {
        return parent;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {

        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;

        Region region = (Region) o;

        return id == region.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", level=" + level +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
