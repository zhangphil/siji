package com.wlqq.huodi.handler;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-30
 */
public class AccessDeniedHandler implements ExceptionHandler {

    public static AccessDeniedHandler instance = new AccessDeniedHandler();

    public static AccessDeniedHandler getInstance() {
        return instance;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        Toast.makeText(HuoDiApplication.getContext(), "您的帐号不能访问此应用", Toast.LENGTH_LONG).show();

        final SavedCredential savedCredential = SavedCredential.getInstance();

        savedCredential.setCredential(null);
        savedCredential.setPrincipal(null);

        final Activity activity = remoteTaskContext.getActivity();
        final Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);

    }
}
