package com.wlqq.huodi.utils;

import android.util.Log;

/**
 * @author xlw
 *         Date: 13-10-16
 */
public class LogUtils {

	private static final boolean IS_PRINT_LOG = true;

	public static void i(String tag, String msg) {
		if (IS_PRINT_LOG)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (IS_PRINT_LOG)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (IS_PRINT_LOG)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (IS_PRINT_LOG)
			Log.i(tag, msg);
	}
}
