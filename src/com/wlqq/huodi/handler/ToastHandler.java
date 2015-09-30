package com.wlqq.huodi.handler;

import android.widget.Toast;

import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class ToastHandler implements ExceptionHandler {

    private static ToastHandler instance = new ToastHandler();

    private ToastHandler() {
    }

    public static ToastHandler getInstance() {
        return instance;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        Toast.makeText(HuoDiApplication.getContext(), errorCode.getMessage(), Toast.LENGTH_LONG).show();
    }
}
