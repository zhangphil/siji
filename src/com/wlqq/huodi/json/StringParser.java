/**
 *
 */
package com.wlqq.huodi.json;

import org.json.JSONException;

/**
 * @author tiger
 */
public final class StringParser implements Parser<String> {

    private static final StringParser instance = new StringParser();

    private StringParser() {
    }

    public static StringParser getInstance() {
        return instance;
    }

    public String parse(String content) throws JSONException {
        return content;
    }

}
