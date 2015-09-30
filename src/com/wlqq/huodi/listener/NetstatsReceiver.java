package com.wlqq.huodi.listener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.NetworkErrorActivity;
import com.wlqq.huodi.utils.ConnectionUtils;
import com.wlqq.huodi.utils.LogUtils;


public class NetstatsReceiver extends BroadcastReceiver {
	private Activity activity;

	public NetstatsReceiver(Activity activity) {
		this.activity = activity;
	}

	public void onReceive(Context context, Intent intent) {
		try {

			final View viewById = activity.findViewById(R.id.no_network_tip);
			if (!ConnectionUtils.isConnected()) {
				viewById.setVisibility(View.VISIBLE);
				viewById.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						activity.startActivity(new Intent(activity, NetworkErrorActivity.class));
					}
				});

			} else {
				viewById.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			LogUtils.e("NetstatsReceiver", e.toString());
		}

	}

}
