package com.wlqq.huodi.handler;

import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public class DelegationHandler implements ExceptionHandler {

    public Map<String, ExceptionHandler> handlerMap = new HashMap<String, ExceptionHandler>();

    public DelegationHandler() {
        init();
    }

    private void init() {
        for (ErrorCode ec : ErrorCode.values()) {
            handlerMap.put(ec.getCode(), getDefaultHandler());
        }

        handlerMap.put(ErrorCode.SESSION_EXPIRED.getCode(), SessionExpiredHandler.getInstance());
        handlerMap.put(ErrorCode.NOT_AUTHENTICATED.getCode(), SessionExpiredHandler.getInstance());
        handlerMap.put(ErrorCode.AP_PERMISSION_SESSION_EXPIRED.getCode(), SessionExpiredHandler.getInstance());
        handlerMap.put(ErrorCode.USERNAME_OR_PWD_WRONG.getCode(), UserNameOrPwdWrongHandler.getInstance());
        handlerMap.put(ErrorCode.INVALID_USER_STATE.getCode(), InvalidUserStateHandler.getInstance());
        handlerMap.put(ErrorCode.CONCURRENT_LOGIN_ERROR.getCode(), ConcurrentLoginHandler.getInstance());
        handlerMap.put(ErrorCode.LOGIN_FROM_OTHER_DEVICE.getCode(), LoginFromOtherDeviceHandler.getInstance());
        handlerMap.put(ErrorCode.DEVICE_NOT_AUTH.getCode(), UserNameOrPwdWrongHandler.getInstance());
    }

    public void registerErrorHandler(ErrorCode errorCode, ExceptionHandler exceptionHandler) {
        handlerMap.put(errorCode.getCode(), exceptionHandler);
    }

    @Override
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext) {
        if (handlerMap.containsKey(errorCode.getCode())) {
            handlerMap.get(errorCode.getCode()).handleError(errorCode, remoteTaskContext);
        } else {
            getDefaultHandler().handleError(errorCode, remoteTaskContext);
        }
    }

    protected ExceptionHandler getDefaultHandler() {
        return ToastHandler.getInstance();
    }
}
