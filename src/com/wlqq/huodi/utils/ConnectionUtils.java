package com.wlqq.huodi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.http.HttpPostFactory;

/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 11-12-28
 */
public class ConnectionUtils {

    /**
     * Determine whether the device is connected to the Internet
     *
     * @return true for yes; otherwise no
     */
    public static boolean isConnected() {
        final Context context = HuoDiApplication.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            HttpPostFactory.setApnName("");
            HttpPostFactory.setWifi(false);
        } else {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                HttpPostFactory.setApnName("");
                HttpPostFactory.setWifi(true);
            } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                HttpPostFactory.setApnName(ApnUtils.getApnType(context));
                HttpPostFactory.setWifi(false);
            }
        }

        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
