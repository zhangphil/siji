package com.wlqq.huodi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.utils.AlarmManagerUtil;

/**
 * User: Hou
 * Date: 13-12-9
 * Time: 下午3:14
 */
public class AutoLocationBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (AuthenticationHolder.getSession() != null) {
                AlarmManagerUtil.sendLocationBroadcast(HuoDiApplication.getContext());
            }
        }
    }
}
