/**
 *
 */
package com.wlqq.huodi.json;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tiger
 */
public final class ArrayParser<T> implements Parser<List<T>> {

    private Parser<T> parser;
    private static String content;

    public ArrayParser(Parser<T> parser) {
        this.parser = parser;
    }

    public static <T> ArrayParser<T> getInstance(Parser<T> parser) {
        return new ArrayParser<T>(parser);
    }


    public static String getParseContent() {
        return content;
    }

    public List<T> parse(String content) throws JSONException {
        final JSONArray jsonArray = new JSONArray(content);
        this.content = content;
        int i = 0, len = jsonArray.length();

        final List<T> array = new ArrayList<T>();

        for (; i < len; i++) {
            array.add(parser.parse(jsonArray.getString(i)));
        }

        return array;
    }

}
