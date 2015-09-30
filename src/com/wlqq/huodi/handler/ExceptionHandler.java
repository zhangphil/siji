package com.wlqq.huodi.handler;

import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.task.RemoteTaskContext;

/**
 * @author xlw
 *         Date: 12-8-10
 */
public interface ExceptionHandler {
    public void handleError(ErrorCode errorCode, RemoteTaskContext remoteTaskContext);
}
