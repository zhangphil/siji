package com.wlqq.huodi.handler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class VehicleAlreadyExistsHandler implements ExceptionHandler {

    private EditText plateNumberEditText;

    public VehicleAlreadyExistsHandler(EditText plateNumberEditText) {
        this.plateNumberEditText = plateNumberEditText;
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        new AlertDialog.Builder(remoteTaskContext.getActivity()).setTitle(R.string.tips)
                .setMessage(String.format(errorCode.getMessage(), HuoDiApplication.getContext().getString(R.string.customer_service_tel))).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                plateNumberEditText.requestFocus();
            }
        }).show();
    }
}
