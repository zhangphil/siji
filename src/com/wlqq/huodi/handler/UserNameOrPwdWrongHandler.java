package com.wlqq.huodi.handler;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class UserNameOrPwdWrongHandler implements ExceptionHandler {

    public static UserNameOrPwdWrongHandler instance = new UserNameOrPwdWrongHandler();

    public static UserNameOrPwdWrongHandler getInstance() {
        return instance;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        Toast.makeText(HuoDiApplication.getContext(), errorCode.getMessage(), Toast.LENGTH_LONG).show();
        final Activity activity = remoteTaskContext.getActivity();
        final Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
