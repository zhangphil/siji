package com.wlqq.huodi.utils;

import android.content.Context;
import android.telephony.TelephonyManager;


import com.wlqq.huodi.app.HuoDiApplication;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * @author Tiger Tang
 *         Date: 12-3-18
 *         Time: 下午5:56
 * @since 0.1.20
 */
public final class DeviceUtils {

	public static String getDeviceFingerprint() {
		final TelephonyManager telephonyManager = (TelephonyManager) HuoDiApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = SharedPreferencesUtil.getDeviceId();
		if (StringUtils.isBlank(deviceId)) {
			String tmDevice = "" + telephonyManager.getDeviceId();
			UUID deviceUuid = new UUID(tmDevice.hashCode(), ((long) tmDevice.hashCode() << 16));
			SharedPreferencesUtil.saveDeviceId(deviceUuid.toString());
			return deviceUuid.toString();
		} else {
			return deviceId;
		}

	}

}
