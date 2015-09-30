package com.wlqq.huodi.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tiger Tang
 *         Date: 12-2-25
 *         Time: 下午3:27
 * @since 0.1.20
 */
public class RegexUtils {

    private static final Pattern REGEX_TEL_NUMBER = Pattern.compile("^\\d{3,4}\\-?\\d{7,8}$");
    private static final Pattern REGEX_MOBILE_NUMBER = Pattern.compile("^1\\d{10}$");
    private static final Pattern REGEX_USERNAME = Pattern.compile("^([\u4E00-\u9FA5]|[\\w\\d]){4,16}$");
    private static final Pattern REGEX_PASSWORD = Pattern.compile("^[\\w\\d]{6,16}$");
    private static final Pattern REGEX_PASSWORD_CONTINUATION = Pattern.compile("(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5,}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5,})\\d");
    private static final Pattern REGEX_PASSWORD_DUPLICATE = Pattern.compile("([0-9a-zA-Z])\\1{5,}");

    public static boolean isValidTelNumber(String s) {
        return REGEX_TEL_NUMBER.matcher(s).matches();
    }

    public static boolean isValidMobileNumber(String s) {
        return REGEX_MOBILE_NUMBER.matcher(s).matches();
    }

    public static boolean isValidUsername(String str) {
        return REGEX_USERNAME.matcher(str).matches();
    }

    public static boolean isValidPassword(String str) {
        return REGEX_PASSWORD.matcher(str).matches();
    }


    public static boolean isChinese(String name) {
        if (StringUtils.isNotEmpty(name)) {
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
            Matcher m = p.matcher(name);
            return m.matches();
        }
        return false;
    }

    public static boolean isCompanyName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5()（）]+");
            Matcher m = p.matcher(name);
            return m.matches();
        }
        return false;
    }

    public static boolean isPasswordDuplicate(String password) {

        return REGEX_PASSWORD_DUPLICATE.matcher(password).matches();
    }

	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

    public static boolean isPasswordContinuation(String password) {

        return REGEX_PASSWORD_CONTINUATION.matcher(password).matches();
    }
}
