package com.wlqq.huodi.exception;

/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 11-12-29
 */
public class ServerInternalException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -953425376944725294L;

    public ServerInternalException() {
    }

    public ServerInternalException(String detailMessage) {
        super(detailMessage);
    }

    public ServerInternalException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerInternalException(Throwable throwable) {
        super(throwable);
    }
}
