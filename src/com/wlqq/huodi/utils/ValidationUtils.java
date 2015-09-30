/**
 *
 */
package com.wlqq.huodi.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * @author tiger
 */
public class ValidationUtils {

    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w\\-]{2,20}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[\\w\\d_!@#\\$%\\^&*\\(\\)]{6,16}$");
    public static final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    public static final Pattern PLATE_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
    public static final Pattern CHINESE_CHARACTER_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+");
    public static final Pattern FIXED_TELEPHONE_NUMBER_PATTERN = Pattern.compile("0\\d{2,3}-?\\d{7,8}|\\d{7,8}");

    public static boolean isValidUsername(final String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(final String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isMobileNO(String mobiles) {
        return MOBILE_NUMBER_PATTERN.matcher(mobiles).matches();
    }

    public static boolean isTruckNO(String truckNum) {
        return StringUtils.length(truckNum) == 5 && PLATE_NUMBER_PATTERN.matcher(truckNum).matches();
    }

    public static boolean isChinese(String name) {
        return StringUtils.isNotEmpty(name) && CHINESE_CHARACTER_PATTERN.matcher(name).matches();
    }

    public static boolean isFixedTelephoneNumber(String number) {
        return FIXED_TELEPHONE_NUMBER_PATTERN.matcher(number).matches();
    }
}
