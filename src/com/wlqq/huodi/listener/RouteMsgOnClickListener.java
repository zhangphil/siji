package com.wlqq.huodi.listener;

import android.app.Activity;
import android.content.Intent;

import com.wlqq.huodi.activity.MapRouteActivity;

/**
 * @author Cai
 *         Date 12-7-19
 */
public class RouteMsgOnClickListener implements ViewItemClickListener {
    private Activity activity;

    public RouteMsgOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick() {
        final Intent intent = new Intent(activity, MapRouteActivity.class);
        intent.setAction("activity");
        activity.startActivity(intent);

    }
}
