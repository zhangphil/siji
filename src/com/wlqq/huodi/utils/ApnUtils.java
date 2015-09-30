package com.wlqq.huodi.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.wlqq.huodi.http.ApnNet;

/**
 * 获取当前手机通过那种方式接入到网络
 *
 * @author xlw
 *         Date: 13-6-18
 */
public class ApnUtils {

    //APN数据访问地址
    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    //获取手机网络接入点类型
    public static String getApnType(Context context) {
        String type = "unknown";

        if (android.os.Build.VERSION.SDK_INT > 16) {
            return type;
        }

        Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
        try {
            c.moveToFirst();
            String apn = c.getString(c.getColumnIndex("apn"));
            if (TextUtils.isEmpty(apn)) {
                type = "unknown";
            } else if (apn.startsWith(ApnNet.CTNET)) {
                type = ApnNet.CTNET;
            } else if (apn.startsWith(ApnNet.CTWAP)) {
                type = ApnNet.CTWAP;
            } else if (apn.startsWith(ApnNet.CMWAP)) {
                type = ApnNet.CMWAP;
            } else if (apn.startsWith(ApnNet.CMNET)) {
                type = ApnNet.CMNET;
            } else if (apn.startsWith(ApnNet.GWAP_3)) {
                type = ApnNet.GWAP_3;
            } else if (apn.startsWith(ApnNet.GNET_3)) {
                type = ApnNet.GNET_3;
            } else if (apn.startsWith(ApnNet.UNIWAP)) {
                type = ApnNet.UNIWAP;
            } else if (apn.startsWith(ApnNet.UNINET)) {
                type = ApnNet.UNINET;
            }
        } catch (Exception e) {
            e.printStackTrace();
            type = "unknown";
        }

        return type;
    }
}
