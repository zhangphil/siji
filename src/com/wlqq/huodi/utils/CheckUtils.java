package com.wlqq.huodi.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.data.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Arena
 * @since v0.1
 *        Date 11-5-27
 */
public class CheckUtils {

    private static final Map<String, String> provCodeMap = new ConcurrentHashMap<String, String>();

    private static final Pattern MALE_GENDER = Pattern.compile("^(\\d{14}[13579]$|\\d{16}[13579](\\d|[Xx])$)");
    private static final Pattern FEMALE_GENDER = Pattern.compile("^(\\d{14}[02468]$|\\d{16}[02468](\\d|[Xx])$)");

    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    static {
	df.setLenient(false);

	provCodeMap.put("11", "北京");
	provCodeMap.put("12", "天津");
	provCodeMap.put("13", "河北");
	provCodeMap.put("14", "山西");
	provCodeMap.put("15", "内蒙古");
	provCodeMap.put("21", "辽宁");
	provCodeMap.put("22", "吉林");
	provCodeMap.put("23", "黑龙江");
	provCodeMap.put("31", "上海");
	provCodeMap.put("32", "江苏");
	provCodeMap.put("33", "浙江");
	provCodeMap.put("34", "安徽");
	provCodeMap.put("35", "福建");
	provCodeMap.put("36", "江西");
	provCodeMap.put("37", "山东");
	provCodeMap.put("41", "河南");
	provCodeMap.put("42", "湖北");
	provCodeMap.put("43", "湖南");
	provCodeMap.put("44", "广东");
	provCodeMap.put("45", "广西");
	provCodeMap.put("46", "海南");
	provCodeMap.put("50", "重庆");
	provCodeMap.put("51", "四川");
	provCodeMap.put("52", "贵州");
	provCodeMap.put("53", "云南");
	provCodeMap.put("54", "西藏");
	provCodeMap.put("61", "陕西");
	provCodeMap.put("62", "甘肃");
	provCodeMap.put("63", "青海");
	provCodeMap.put("64", "宁夏");
	provCodeMap.put("65", "新疆");
	provCodeMap.put("71", "台湾");
	provCodeMap.put("81", "香港");
	provCodeMap.put("82", "澳门");
	provCodeMap.put("91", "国外");
    }

    public static boolean check(String icNumber) {
	icNumber = normalizeICNumber(icNumber);
	return lengthCheck(icNumber) && verificationCodeCheck(icNumber) && areaCheck(icNumber) && birthdayCheck(icNumber);
    }

    /**
     * Normalize ID number, make them all 18-bit long
     *
     * @param idNumber original ID number
     * @return normalized ID number
     */
    public static String normalizeICNumber(String idNumber) {
	if (idNumber.length() == Constants.SHORT_FORMAT_IC_LENGTH) {
	    // 15 update to 18
	    String eighteenIdCard = idNumber.substring(0, 6);
	    eighteenIdCard = eighteenIdCard + "19";
	    eighteenIdCard = eighteenIdCard + idNumber.substring(6, 15);
	    eighteenIdCard = eighteenIdCard + calcVerificationCode(eighteenIdCard);
	    idNumber = eighteenIdCard;
	}
	return idNumber.toUpperCase();
    }

    public static boolean lengthCheck(String inputIdentityCard) {
	final int len = inputIdentityCard.length();
	return len == Constants.SHORT_FORMAT_IC_LENGTH || len == Constants.NORMAL_FORMAT_IC_LENGTH;
    }

    public static boolean verificationCodeCheck(String idNumber) {
	String verificationCode = idNumber.substring(17, 18);
	return verificationCode.equals(calcVerificationCode(idNumber));
    }

    public static boolean birthdayCheck(String IDNumber) {
	String birthdayString = IDNumber.substring(6, 14);
	try {
	    df.parse(birthdayString);
	    return true;
	} catch (ParseException e) {
	    return false;
	}
    }

    public static boolean areaCheck(String idNumber) {
	String provCode = idNumber.substring(0, 2);
	return provCodeMap.containsKey(provCode);
    }

    // get verify
    public static String calcVerificationCode(String idCard) {
	final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
	final int[] ai = new int[18];
	int remaining = 0;
	if (idCard.length() == 18) {
	    idCard = idCard.substring(0, 17);
	}
	if (idCard.length() == 17) {
	    int sum = 0;
	    for (int i = 0; i < 17; i++) {
		String k = idCard.substring(i, i + 1);
		try {
		    ai[i] = Integer.parseInt(k);
		} catch (NumberFormatException e) {
		}
	    }
	    for (int i = 0; i < 17; i++) {
		sum = sum + wi[i] * ai[i];
	    }
	    remaining = sum % 11;
	}
	return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    public static boolean isConnected(Activity activity) {
	ConnectivityManager manager = (ConnectivityManager) activity
		.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo info = manager.getActiveNetworkInfo();
	return !(info == null || !info.isConnected());
    }


    /*
		if (!CheckUtils.isConnected(activity)) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setPositiveButton(activity.getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					dialog.dismiss();
				}
			});
			builder.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setTitle(activity.getResources().getString(R.string.err_no_available_networks));
			builder.show();

			return false;
		}

		return true;
*/
    public static boolean isConnected() {
	ConnectivityManager connectivityManager = (ConnectivityManager) HuoDiApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	if (activeNetworkInfo != null) {
	    return activeNetworkInfo.isConnected();
	}
	return false;
    }


}
