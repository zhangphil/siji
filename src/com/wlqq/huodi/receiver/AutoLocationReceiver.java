package com.wlqq.huodi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.locate.WRequestLocationTask;
import com.wlqq.huodi.task.SubmitLocationTask;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.task.TaskResult;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.WFileUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * User: Hou
 * Date: 13-12-9
 * Time: 下午3:14
 */
public class AutoLocationReceiver extends BroadcastReceiver {

	private static WRequestLocationTask locationTask;

	private static final String TAG = "AutoLocationReceiver";

	private static final int DEFAULT_STOP_TIME = 20;
	private static final int DEFAULT_START_TIME = 6;
	private static final int DELAY_SCOPE_TIME = 15 * 60 * 1000;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("AutoLocationReceiver", "onReceive......");
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		if (hour > DEFAULT_START_TIME && hour < DEFAULT_STOP_TIME) {
			getLocationTask().execute();
		}
	}

	private WRequestLocationTask getLocationTask() {
		if (locationTask == null) {
			locationTask = new WRequestLocationTask(false) {
				@Override
				public void succeed() {
					super.succeed();
					Log.v(TAG, "get location succeed , auto_attendance now");
					delayedSubmitLocation();
				}

				@Override
				public void failedToAddress() {
					super.failedToAddress();
					delayedSubmitLocation();
				}

				@Override
				public void failed() {
					super.failed();
					Log.v(TAG, "get location failed , re-do get location after one minute");
				}
			};
		}
		return locationTask;
	}

	private void delayedSubmitLocation() {
		long delay = new Random().nextInt(DELAY_SCOPE_TIME);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				final AddressComponent address_temp = LocationHolder.getLOCATION();
				submitLocation(address_temp.getLatitude(), address_temp.getLongitude(), address_temp.getFormattedAddress());
			}
		}, delay);
	}

	private void submitLocation(final double latitude, final double longitude, final String formattedAddress) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lat", latitude);
		paramMap.put("lng", longitude);
		paramMap.put("address", formattedAddress);

		new SubmitLocationTask() {
			@Override
			protected void onError(ErrorCode errorCode) {
				super.onError(errorCode);
				storeLocation(latitude, longitude, formattedAddress);
			}

			@Override
			protected void onError(TaskResult.Status status) {
				super.onError(status);
				storeLocation(latitude, longitude, formattedAddress);
			}
		}.execute(new TaskParams(paramMap));
	}

	private void storeLocation(double latitude, double longitude, String formattedAddress) {
		StringBuilder cacheLocation = new StringBuilder();
		cacheLocation.append(System.currentTimeMillis()).append(",");
		cacheLocation.append(latitude).append(",");
		cacheLocation.append(longitude).append(",");
		cacheLocation.append(formattedAddress).append(";");
		WFileUtils.writeStringAppend(HuoDiConstants.LOCATION_CACHE_FILE, cacheLocation.toString());
	}

}
