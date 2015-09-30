package com.wlqq.huodi.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cai
 *         Date 12-5-11
 */
public class EditTextCheckUtils {

    public static boolean isMobileNO(String mobiles) {
	if (StringUtils.isNotBlank(mobiles)) {
	    Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
	    Matcher m = p.matcher(mobiles);
	    return m.matches();
	}
	return false;
    }

    public static boolean isTruckNO(String truckNum) {
	if (truckNum.length() == 7) {
	    Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
	    Pattern p1 = Pattern.compile("^[A-Za-z]+$");
	    Matcher matcher = p1.matcher(truckNum.substring(1, 2));
	    final boolean matches = matcher.matches();
	    if (matches) {
		Matcher m = p.matcher(truckNum.substring(2, truckNum.length()));
		return m.matches();
	    } else {
		return false;
	    }
	}
	return false;
    }

    public static boolean isChinese(String name) {
	if (StringUtils.isNotEmpty(name)) {
	    Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
	    Matcher m = p.matcher(name);
	    return m.matches();
	}
	return false;
    }

    /**
     * Replacing SBC characters to DBC characters
     *
     * @param srcString source string object
     * @return string that had been replaced
     */
    public static String replaceDBCWithSBC(String srcString) {
	if (StringUtils.isNotBlank(srcString)) {
	    char c[] = srcString.toCharArray();
	    for (int i = 0; i < c.length; i++) {
		if (c[i] == '\u3000') {
		    c[i] = ' ';
		} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
		    c[i] = (char) (c[i] - 65248);

		}
	    }
	    return new String(c);
	}

	return new String();
    }
}
