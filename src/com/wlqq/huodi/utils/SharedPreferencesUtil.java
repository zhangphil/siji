package com.wlqq.huodi.utils;

import android.content.SharedPreferences;

import com.wlqq.huodi.app.HuoDiApplication;

/**
 * User: hou
 * Date: 14-2-21
 * Time: 上午9:59
 */
public class SharedPreferencesUtil {
    private static final String TAG = "Preferences";

    public static void saveFileDownLoadUrl(String Url, String tag) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag, Url);
        editor.commit();
    }

    public static String getFileDownLoadUrl(String tag) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        return sharedPreferences.getString(tag, "");
    }


    public static void saveDeviceId(String result) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HuoDiConstants.DEVICE_ID, result);
        editor.commit();
    }

    public static String getDeviceId() {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        return sharedPreferences.getString(HuoDiConstants.DEVICE_ID, "");
    }

    public static void savePln(String result) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HuoDiConstants.PLN, result);
        editor.commit();
    }

    public static String getPln() {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        return sharedPreferences.getString(HuoDiConstants.PLN, "");
    }

    public static String getOldImageDownLoadUrl(String tag) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        return sharedPreferences.getString(tag, "");
    }


    public static void saveImageDownLoadUrl(String Url, String tag) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag, Url);
        editor.commit();
    }

    public static void saveSplashAdvLastPlayNum(int num) {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HuoDiConstants.ADV_SPLASH_LAST_PLAY_NUM, num);
        editor.commit();
    }

    public static int getSplashAdvLastPlayNum() {
        SharedPreferences sharedPreferences = HuoDiApplication.getPreferences();
        return sharedPreferences.getInt(HuoDiConstants.ADV_SPLASH_LAST_PLAY_NUM, 0);
    }

}
