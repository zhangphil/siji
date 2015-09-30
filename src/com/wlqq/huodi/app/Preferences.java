package com.wlqq.huodi.app;

import com.wlqq.huodi.data.Constants;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public class Preferences {

    public static String getString(String key, String defaultValue) {
        return HuoDiApplication.getPreferences().getString(key, defaultValue);
    }

    public static long getLong(String name) {
        return HuoDiApplication.getPreferences().getLong(name, Constants.INVALID_INTEGER_VALUE);
    }

    public static long getLong(String name, long defValue) {
        return HuoDiApplication.getPreferences().getLong(name, defValue);
    }

    public static int getInt(String name, int defValue) {
        return HuoDiApplication.getPreferences().getInt(name, defValue);
    }

    public static boolean getBoolean(String key, final boolean defaultValue) {
        return HuoDiApplication.getPreferences().getBoolean(key, defaultValue);
    }

    public static void set(final String name, final long value) {
        HuoDiApplication.getPreferences().edit().putLong(name, value).commit();
    }

    public static void set(final String name, final int value) {
        HuoDiApplication.getPreferences().edit().putInt(name, value).commit();
    }

    public static void set(String key, boolean value) {
        HuoDiApplication.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void set(String key, String value) {
        HuoDiApplication.getPreferences().edit().putString(key, value).commit();
    }

}
