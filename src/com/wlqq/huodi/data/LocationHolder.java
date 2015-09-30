package com.wlqq.huodi.data;

import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.Region;

/**
 * @author Tiger Tang
 *         Date: 12-1-7
 *         Time: 下午7:01
 * @since 0.1.20
 */
public class LocationHolder {

    private static boolean LOCATED = false;
    public static AddressComponent LOCATION;

    public static boolean isLOCATED() {
        return LOCATED;
    }

    public static void setLOCATED(boolean LOCATED) {
        LocationHolder.LOCATED = LOCATED;
    }

    public static AddressComponent getLOCATION() {
        return LOCATION;
    }

    public static void setLOCATION(AddressComponent LOCATED_ADDRESS) {

        LocationHolder.LOCATION = LOCATED_ADDRESS;
    }

    public static Region getUSER_SELECTED_REGION() {
        long usr = Preferences.getLong("USER_SELECTED_REGION");

        return RegionManager.isRegion(usr) ? RegionManager.getRegion(usr) : RegionManager.getRegion(4101L);
    }

    public static void setUSER_SELECTED_REGION(Region USER_SELECTED_REGION) {
        Preferences.set("USER_SELECTED_REGION", USER_SELECTED_REGION.getId());
    }
}
