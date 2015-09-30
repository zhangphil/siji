package com.wlqq.huodi.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


import com.wlqq.huodi.app.HuoDiApplication;

import java.util.regex.Pattern;

/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 11-12-28
 */
public class VersionUtils {

    private static final Pattern VERSION_PATTERN = Pattern.compile("^\\d+(\\.\\d+){0,2}$");

    public static String getClientOSVersion() {
        return HuoDiConstants.ANDROID_VERSION_PREFIX + Build.VERSION.RELEASE;
    }

    public static String getCurrentVersion() {
        final Context context = HuoDiApplication.getContext();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return HuoDiConstants.VERSION;
        }
    }

    public static int getCurrentVersionCode() {
        final Context context = HuoDiApplication.getContext();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * Check whether the input version is newer than current one.
     *
     * @param version1 version 1 to be compared
     * @return true if the first one is newer than the second; otherwise false
     */
    public static boolean isNewVersion(String version1) {
        String version2 = getCurrentVersion();
        if (isValidVersion(version1) && isValidVersion(version2)) {
            Version v1 = new Version(version1);
            Version v2 = new Version(version2);

            return v1.compare(v2);
        } else {
            return false;
        }
    }

    /**
     * Check whether the version is of correct naming convention
     *
     * @param version version in <code>String</code> type
     * @return true if valid; otherwise false
     */
    public static boolean isValidVersion(String version) {
        return VERSION_PATTERN.matcher(version).matches();
    }

    private static final class Version {
        private int major;
        private int minor;
        private int build;

        private Version(String version) {
            final String[] a = version.split("\\.");
            final int len = a.length;

            major = Integer.valueOf(a[0]);

            if (len > 1) {
                minor = Integer.valueOf(a[1]);
            }

            if (len > 2) {
                build = Integer.valueOf(a[2]);
            }
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public int getBuild() {
            return build;
        }

        public boolean compare(Version version2) {
            return major > version2.getMajor() || minor > version2.getMinor() || build > version2.getBuild();
        }
    }

}
