package com.wlqq.huodi.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author xlw
 *         Date 13-12-10
 */
public class WFileUtils {

    private static final String TAG = "FileUtils";

    public static void writeStringAppend(String filePath, String content) {
        writeStringInFile(filePath, content, true);
    }

    public static void clearTextFile(String filePath) {
        writeStringInFile(filePath, "", false);
    }

    public static void writeStringInFile(String filePath, String content, boolean append) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append)));
            out.write(content);
        } catch (Exception e) {
            Log.e(TAG, "failed due to : " + e);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                Log.e(TAG, "failed due to : " + e);
            }
        }
    }

    public static String getFileString(String filePath) {
        File file = new File(filePath);
        try {
            return org.apache.commons.io.FileUtils.readFileToString(file);
        } catch (IOException e) {
            Log.e(TAG, "failed due to : " + e);
        }
        return "";
    }
}
