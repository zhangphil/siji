package com.wlqq.huodi.task;

import java.util.Map;

/**
 * @author Tiger Tang
 *         Date: 12-1-19
 *         Time: 上午9:28
 * @since 0.1.20
 */
public class TaskParams {

    private Map<String, Object> httpParams;


	public Map<String, Object> getHttpParams() {
        return httpParams;
    }

    public void setHttpParams(Map<String, Object> httpParams) {
        this.httpParams = httpParams;
    }

    public TaskParams(Map<String, Object> httpParams) {
        this.httpParams = httpParams;
    }
}
