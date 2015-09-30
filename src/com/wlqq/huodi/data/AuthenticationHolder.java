package com.wlqq.huodi.data;

import android.util.Log;


import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.json.SessionParser;
import com.wlqq.huodi.json.UserProfileParser;
import com.wlqq.huodi.utils.HuoDiConstants;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Tiger Tang
 *         Date: 12-1-4
 *         Time: 下午2:44
 * @since 0.1.20
 */
public class AuthenticationHolder {

    private static final String TAG = "AuthenticationHolder";

    private static Session session;
    private static UserProfile profile;
    private static long uid;
    private static boolean login;
    private static int stopTime = 21;
    private static int startTime = 7;

    public static void setLocationStopTime(int time) {
        Preferences.set("stopTime", time);
    }

    public static int getLocationStopTime() {
        return Preferences.getInt("stopTime", stopTime);
    }

    public static void setLocationStartTime(int time) {
        Preferences.set("startTime", time);
    }

    public static int getLocationStartTime() {
        return Preferences.getInt("startTime", startTime);
    }

    public static void setEmpty(boolean empty) {
        Preferences.set("isEmpty", empty);
    }

    public static boolean isEmpty() {
        return Preferences.getBoolean("isEmpty", false);
    }

    public static long getLocationInterval() {
        if (isEmpty()) {
            return getLocationEmptyInterval();
        } else {
            return getLocationFullInterval();
        }
    }

    public static long getLocationEmptyInterval() {
        return Preferences.getLong("locationEmptyInterval", 300000L);
    }

    public static long getLocationFullInterval() {
        return Preferences.getLong("locationFullInterval", 1800000L);
    }

    public static void setLocationEmptyInterval(long interval) {
        Preferences.set("locationEmptyInterval", interval);
    }

    public static void setLocationFullInterval(long interval) {
        Preferences.set("locationFullInterval", interval);
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        AuthenticationHolder.login = login;
    }

    private static boolean isAuthenticated() {
        return session == null;
    }

    public static Session getSession() {
        if (session == null) {
            return getPreSession();
        }
        return session;
    }

    public static void setSession(Session s) {
        storeSession(s);
        session = s;
    }

    public static UserProfile getProfile() {
        if (profile == null) {
            return getPreUserProfile();
        }
        return profile;
    }

    public static void setProfile(UserProfile userProfile) {
        storeProfile(userProfile);
        profile = userProfile;
    }

    public static void storeSession(Session session) {
        try {
            final JSONObject value = new JSONObject();
            if (session != null) {
                value.put("id", session.getId());
                value.put("token", session.getToken());
                Preferences.set("session", value.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "failed due to : " + e);
        }
    }

    public static void storeProfile(UserProfile userProfile) {
        try {
            final JSONObject value = new JSONObject();
            if (userProfile != null) {
                value.put("id", userProfile.getId());
                value.put("un", userProfile.getUsername());
                value.put("ut", userProfile.getType());
                value.put("pid", userProfile.getProvinceId());
                value.put("cid", userProfile.getCityId());
                value.put("cntid", userProfile.getCountyId());
                value.put("addr", userProfile.getAddress());
                value.put("cp", userProfile.getContactor());
                value.put("m", userProfile.getMobile());
                value.put("m1", userProfile.getMobile1());
                value.put("m2", userProfile.getMobile2());
                value.put("m3", userProfile.getMobile3());
                value.put("icname", userProfile.getIcName());
                value.put("icnum", userProfile.getIcNum());
                value.put("tel", userProfile.getTelephone());
                value.put("qq", userProfile.getQq());
                value.put("member", userProfile.isMember());
                value.put("voip", userProfile.getVoip());
                value.put("sti", userProfile.getSystemTime());
                value.put("sn", userProfile.getMembershipName());
                Date membershipStartTime = userProfile.getMembershipStartTime();
                if (membershipStartTime != null) {
                    value.put("sst", HuoDiConstants.DF_yyyy_MM_dd.format(membershipStartTime));
                }
                Date membershipExpireTime = userProfile.getMembershipExpireTime();
                if (membershipExpireTime != null) {
                    value.put("set", HuoDiConstants.DF_yyyy_MM_dd.format(membershipExpireTime));
                }

                value.put("vb_n", userProfile.getVehicleBrandName());
                value.put("vn", userProfile.getPlateNumber());
                value.put("eb_n", userProfile.getEnginePower());
                value.put("ep", userProfile.getEnginePower());
                value.put("ct_n", userProfile.getVehicleTypeName());
                value.put("vl_n", userProfile.getVehicleLengthName());
                value.put("wn", userProfile.getWheelNumber());
                value.put("vlt", userProfile.getVehicleLicenseTime());
                Preferences.set("userProfile", value.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "failed due to : " + e);
        }
    }

    private static Session getPreSession() {
        try {
            final String content = Preferences.getString("session", "");
            if (StringUtils.isNotBlank(content))
                return SessionParser.getInstance().parse(content);
        } catch (JSONException e) {
            Log.e(TAG, "failed due to : " + e);
        }
        return new Session();
    }

    private static UserProfile getPreUserProfile() {
        try {
            final String content = Preferences.getString("userProfile", "");
            if (StringUtils.isNotBlank(content))
                return UserProfileParser.getInstance().parse(content);
        } catch (JSONException e) {
            Log.e(TAG, "failed due to : " + e);
        }
        return new UserProfile();
    }

}
