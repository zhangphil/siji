package com.wlqq.huodi.data;

import android.os.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public class Constants {
    public static final int INVALID_INTEGER_VALUE = -1;
    public static final int SHORT_FORMAT_IC_LENGTH = 15;
    public static final int NORMAL_FORMAT_IC_LENGTH = 18;

    public static final String UNIWAP_PROXY_SERVER = "10.0.0.172"; // cmwap、uniwap和3gwap所用代理地址都10.0.0.172:80

    public static final String CTWAP_PROXY_SERVER = "10.0.0.200"; // ctwap所用代理地址为10.0.0.200：80

    public static String CAMERA_DIR = Environment.getExternalStorageDirectory() + "/huodi/";

    public static DateFormat DATEFORMAT = new SimpleDateFormat("yyyy年MM月dd日  hh:mm");

}
