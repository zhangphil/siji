package com.wlqq.huodi.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Hou
 * Date: 13-12-9
 * Time: 下午3:17
 */
public class AlarmUtils {

    public static void setAlarmTime(Context context, Class destination, int requestCode, Calendar alarmTime, long interval) {

        Intent intent = new Intent(context, destination);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final long millis = getTimeInMillisAfterCorrect(alarmTime, interval);
        Date date = new Date(millis);
        String format = "yyyy-MM-dd HH:mm:ss";
        final String format1 = new SimpleDateFormat(format).format(date);
        Log.v("AlarmUtils", format1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millis, interval, pendingIntent);

    }

    private static long getTimeInMillisAfterCorrect(Calendar alarmTime, long interval) {
        long timeInMillis = alarmTime.getTimeInMillis();
        final boolean b = timeInMillis < System.currentTimeMillis();
        return b ? timeInMillis + interval : timeInMillis;
    }

}
