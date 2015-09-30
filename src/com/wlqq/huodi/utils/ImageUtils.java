package com.wlqq.huodi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.*;

/**
 * @author xlw
 *         Date: 12-5-2
 */
public class ImageUtils {

    public static enum BitmapSizeType {

        ORIGINAL(800),
        TWO_HUNDRED(200),
        FORTY(40);

        private int size;

        private BitmapSizeType(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }


    private static final String TAG = "ImageUtils";

    public static File createFile(String fileName) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!file.getParentFile().exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    public static File bitmapToFile(Bitmap bitmap, String path, int quality) {
        if (bitmap == null)
            return null;

        File file = null;
        BufferedOutputStream bos = null;
        try {
            file = SDCardUtils.createSDFile(path);
            if (file == null) {
                file = createFile(path);
            }
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
        } catch (Throwable e) {
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File compressImageFile(File original) {
        final String path = original.getAbsolutePath();
        final Bitmap bitmap = getBitmap(original, 3, BitmapSizeType.ORIGINAL);
        return bitmapToFile(bitmap, path, 80);
    }

    public static File compressImageFile(File original, int radio, String dtPath, BitmapSizeType sizeType) {
        final Bitmap bitmap = getBitmap(original, radio, sizeType);
        return bitmapToFile(bitmap, dtPath, 80);
    }


    public static Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android123");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            getRequest.abort();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return bitmap;
    }

    public static Bitmap getBitmap(File file, int radio, BitmapSizeType SizeType) {
        Bitmap bitmap = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inSampleSize = radio;
            decodeOptions.inPurgeable = true;
            decodeOptions.inInputShareable = true;
//			decodeOptions.inPreferQualityOverSpeed = true;
//            decodeOptions.inJustDecodeBounds =true;
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, decodeOptions);
            decodeOptions.inPreferredConfig = Bitmap.Config.ARGB_4444;
            final int width = decodeOptions.outWidth;
            final int height = decodeOptions.outHeight;
            final int new_width;
            final int new_height;
            if (width < height) {
                new_height = height * SizeType.getSize() / width;
                new_width = SizeType.getSize();
            } else {
                new_width = width * SizeType.getSize() / height;
                new_height = SizeType.getSize();
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, new_width, new_height, true);

        } catch (FileNotFoundException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (Throwable t) {
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return bitmap;
    }

    /**
     * 复制文件
     *
     * @param srFile 源文件
     * @param dtFile 目标文件
     */
    public static void copyFile(String srFile, String dtFile) {

        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            File dir = f2.getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException e) {
        }
    }

}
