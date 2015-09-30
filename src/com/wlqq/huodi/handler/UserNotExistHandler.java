package com.wlqq.huodi.handler;

import android.app.Activity;
import android.content.Intent;

import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-13
 */
public class UserNotExistHandler implements ExceptionHandler {

    private static final String TAG = "userNotExist";

    private UserNotExistHandler() {

    }

    private static UserNotExistHandler instance = new UserNotExistHandler();

    public static UserNotExistHandler getInstance() {
        return instance;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {

        final Activity activity = remoteTaskContext.getActivity();
        final Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
