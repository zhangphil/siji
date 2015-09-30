package com.wlqq.huodi.json;

import org.json.JSONException;

/**
 * @author Tiger Tang
 * @since 110612 Date: 11-10-30
 */
public interface Parser<T>  {

	/**
	 * Parse <code>org.json.JSONObject</code> to specified type object
	 *
	 * @param content source of json string
	 * @return object of specified type
	 * @throws org.json.JSONException raise when parse error occurred
	 */
	T parse(String content) throws JSONException;

}
