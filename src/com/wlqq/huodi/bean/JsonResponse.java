package com.wlqq.huodi.bean;


/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 11-12-29
 */
public class JsonResponse<T> {

    private String status;
    private T content;
    private int errorCode;

    public JsonResponse(String status, T content) {
        this.status = status;
        this.content = content;
    }

    public JsonResponse(String status, int errorCode) {
        super();
        this.status = status;
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
