package com.wlqq.huodi.utils;

/**
 * author cai
 * Time  下午3:25
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.receiver.SendLocationReceiver;

import java.util.Calendar;

public class AlarmManagerUtil {
    public static int SENDUPDATE = 0;
    public static int SENDHEARTBEAT = 1;
    public static int CHECKHEARTBEAT = 2;
    public static int SENDLOCATION = 3;

    public static AlarmManager getAlarmManager(Context ctx) {
        return (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    }

    public static void sendLocationBroadcast(Context ctx) {
        AlarmManager am = getAlarmManager(ctx);
        Intent i = new Intent(ctx, SendLocationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, SENDLOCATION, i, 0);
        long locationInterval = AuthenticationHolder.getLocationInterval();
        long triggerAtTime = System.currentTimeMillis() + locationInterval;
        am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
    }

    public static void sendLocationBroadcastNextDay(Context ctx) {
        AlarmManager am = getAlarmManager(ctx);
        Intent i = new Intent(ctx, SendLocationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, SENDLOCATION, i, 0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, AuthenticationHolder.getLocationStartTime());
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}

