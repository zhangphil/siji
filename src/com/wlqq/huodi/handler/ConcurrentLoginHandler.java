package com.wlqq.huodi.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;


/**
 * @author Tiger Tang
 *         Date: 12-8-25
 *         Time: 上午12:20
 */
public class ConcurrentLoginHandler implements ExceptionHandler {

    private static ExceptionHandler instance = new ConcurrentLoginHandler();

    public static ExceptionHandler getInstance() {
        return instance;
    }

    private ConcurrentLoginHandler() {
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {

        final Activity activity = remoteTaskContext.getActivity();

        if (activity != null) {
            new AlertDialog.Builder(activity).
                    setMessage(errorCode.getMessage()).
                    setPositiveButton(activity.getText(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final Intent intent = new Intent(activity, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            HuoDiApplication.getContext().startActivity(intent);
                            activity.finish();
                        }
                    }).show();
        } else {
            Toast.makeText(HuoDiApplication.getContext(), errorCode.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
