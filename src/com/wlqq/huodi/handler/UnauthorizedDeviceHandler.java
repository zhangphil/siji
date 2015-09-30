package com.wlqq.huodi.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;
/**
 * @author xlw
 *         Date: 12-8-10
 */
public class UnauthorizedDeviceHandler implements ExceptionHandler {

	public static UnauthorizedDeviceHandler instance = new UnauthorizedDeviceHandler();

	public static UnauthorizedDeviceHandler getInstance() {
		return instance;
	}

	@Override
	public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
		final Activity activity = remoteTaskContext.getActivity();
		new AlertDialog.Builder(activity).setTitle(R.string.tips)
				.setMessage(String.format(errorCode.getMessage(), HuoDiApplication.getContext().getString(R.string.customer_service_tel))).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				activity.startActivity(new Intent(activity, LoginActivity.class));
				activity.finish();
			}
		}).show();
	}
}
