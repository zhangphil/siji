package com.wlqq.huodi.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 12-5-2
 */
public class SDCardUtils {
    public static String getSDPath() {
        // 得到当前外部存储设备的目录
        return Environment.getExternalStorageDirectory() + File.separator;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws java.io.IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        if (isSDCardAvailable()) {
            File file = new File(fileName);
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (!file.getParentFile().exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
            return file;
        } else {
            return null;
        }
    }


    /**
     * 判断手机是否存在SD卡，并是可读写的
     *
     * @return true可读写，false不可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    
    public static void deleteSDCardFolder(File dir){
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                File temp =  new File(dir, children[i]);
                if(temp.isDirectory())
                {
                    deleteSDCardFolder(temp);
                }
                else
                {
                    boolean b = temp.delete();
                    if(b == false)
                    {
                    }
                }
            }

            dir.delete();
        }
    }
}
