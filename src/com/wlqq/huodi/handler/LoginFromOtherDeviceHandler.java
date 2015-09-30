package com.wlqq.huodi.handler;

import android.app.Activity;
import android.content.Intent;

import com.wlqq.huodi.activity.DialogActivity;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author cai
 *         Date: 14-7-17
 */
public class LoginFromOtherDeviceHandler implements ExceptionHandler {

    private static final String TAG = LoginFromOtherDeviceHandler.class.getSimpleName();
    public static LoginFromOtherDeviceHandler instance = new LoginFromOtherDeviceHandler();
    public static boolean isShow = false;

    public static LoginFromOtherDeviceHandler getInstance() {

        return instance;
    }

    @Override
    public void handleError(final ErrorCode errorCode, final RemoteTaskContext remoteTaskContext) {


        final Activity activity = remoteTaskContext.getActivity();
        DialogActivity.errorCode = errorCode;
        DialogActivity.remoteTaskContext = remoteTaskContext;
        if (!isShow) {
            activity.startActivity(new Intent(activity, DialogActivity.class));
            isShow = true;
        }
    }
}



