package com.wlqq.huodi.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;


import com.wlqq.huodi.R;
import com.wlqq.huodi.activity.LoginActivity;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;
import com.wlqq.huodi.utils.HuoDiConstants;

import org.apache.commons.lang.StringUtils;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class ServiceExpiredHandler implements ExceptionHandler {

    public static ServiceExpiredHandler instance = new ServiceExpiredHandler();

    public static ServiceExpiredHandler getInstance() {
        return instance;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        new AlertDialog.Builder(HuoDiApplication.getContext()).setTitle(R.string.saved)
                .setMessage(String.format(HuoDiApplication.getContext().getString(R.string.err_service_expired), HuoDiApplication.getContext().getString(R.string.customer_service_tel))).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public static class ServiceExpiredChild extends ServiceExpiredHandler {

//       private EditText usernameEditText;
//
//       private EditText passwordEditText;
//
//       private String plateNumber;

        public ServiceExpiredChild() {
        }

        public ServiceExpiredChild(EditText usernameEditText, EditText passwordEditText, String plateNumber) {
//           this.usernameEditText = usernameEditText;
//           this.passwordEditText = passwordEditText;
//           this.plateNumber = plateNumber;

            SavedCredential.getInstance().setAuthType(SavedCredential.AuthType.WULIUQQ);
            if (usernameEditText != null) {

                SavedCredential.getInstance().setPrincipal(usernameEditText.getText().toString());
                SavedCredential.getInstance().setCredential(passwordEditText.getText().toString());
            } else {
                if (StringUtils.isNotEmpty(plateNumber)) {
                    SavedCredential.getInstance().setPrincipal(plateNumber);
                    SavedCredential.getInstance().setCredential(passwordEditText.getText().toString());
                }
            }

        }

        @Override
        public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
            Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
            final Activity activity = remoteTaskContext.getActivity();
            new AlertDialog.Builder(activity).setTitle(R.string.tips).setMessage(String.format(HuoDiApplication.getContext().getString(R.string.err_service_expired), HuoDiApplication.getContext().getString(R.string.customer_service_tel))).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                }
            }).show();
        }
    }

    public static class ServiceExpiredDriverChild extends ServiceExpiredHandler {
        @Override
        public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
            Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);

            final Activity activity = remoteTaskContext.getActivity();
            new AlertDialog.Builder(activity).setTitle(R.string.tips).setMessage(String.format(HuoDiApplication.getContext().getString(R.string.err_service_expired), HuoDiApplication.getContext().getString(R.string.customer_service_tel))).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    activity.startActivity(new Intent(activity,LoginActivity.class));
                    activity.finish();
                }
            }).show();
        }
    }
}
