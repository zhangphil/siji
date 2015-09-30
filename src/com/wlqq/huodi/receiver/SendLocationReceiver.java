/*
 * 
 */
package com.wlqq.huodi.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.locate.WRequestLocationTask;
import com.wlqq.huodi.task.SubmitLocationTask;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.utils.AlarmManagerUtil;
import com.wlqq.huodi.utils.LogUtils;

import java.util.Calendar;
import java.util.HashMap;

public class SendLocationReceiver extends BroadcastReceiver {
    private final String TAG = SendLocationReceiver.class.getSimpleName();
    private static WRequestLocationTask locationTask;

    private static final int DEFAULT_STOP_TIME = AuthenticationHolder.getLocationStopTime();

    public void onReceive(Context context, Intent intent) {
        getLocationTask().execute();

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if (hour < DEFAULT_STOP_TIME) {
            AlarmManagerUtil.sendLocationBroadcast(context);
        } else {
            AlarmManagerUtil.sendLocationBroadcastNextDay(context);
        }

    }


    private WRequestLocationTask getLocationTask() {
        if (locationTask == null) {
            locationTask = new WRequestLocationTask(false) {
                @Override
                public void succeed() {
                    super.succeed();
                    AddressComponent location = LocationHolder.getLOCATION();
                    submitLocation(location.getLatitude(), location.getLongitude(), location.getFormattedAddress());
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


    private void submitLocation(final double latitude, final double longitude, final String formattedAddress) {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lat", latitude);
        paramMap.put("lng", longitude);
        paramMap.put("address", formattedAddress);

        new SubmitLocationTask().execute(new TaskParams(paramMap));
    }


    /**
     * 取消定时执行(有如闹钟的取消)
     *
     * @param ctx
     */
    public static void cancelSendLocationBroadcast(Context ctx) {
        try {
            AlarmManager am = (AlarmManager) ctx.getSystemService(HuoDiApplication.getContext().ALARM_SERVICE);
            Intent i = new Intent(ctx, SendLocationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, AlarmManagerUtil.SENDLOCATION, i, 0);
            am.cancel(pendingIntent);
        } catch (Exception e) {
            LogUtils.e("", e.toString());
        }
    }

}
