package com.wlqq.huodi.exception;

/**
 * @author Tiger Tang
 *         Date: 12-1-4
 *         Time: 上午11:36
 * @since 0.1.20
 */
public class WuliuQQException extends Exception {

    private ErrorCode errorCode;

    public WuliuQQException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
