package com.wlqq.huodi.json;

import org.json.JSONException;

/**
 * @author Tiger Tang
 *         Date: 12-1-4
 *         Time: 下午6:50
 * @since 0.1.20
 */
public final class NULLParser implements Parser<Void> {

    private static final NULLParser instance = new NULLParser();

    private NULLParser() {
    }

    public static NULLParser getInstance() {
        return instance;
    }

    public Void parse(String content) throws JSONException {
        return null;
    }
}
