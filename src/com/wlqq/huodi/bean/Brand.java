package com.wlqq.huodi.bean;

import java.io.Serializable;

/**
 * @author Tiger Tang
 *         Date: 12-1-12
 *         Time: 上午9:52
 * @since 0.1.20
 */
public class Brand implements Serializable {

    public static enum Type {
        VEHICLE,
        ENGINE
    }

    private long id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brand)) {
            return false;
        }
        Brand brand = (Brand) o;

        return id == brand.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Brand");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
