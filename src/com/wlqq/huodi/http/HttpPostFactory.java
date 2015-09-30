package com.wlqq.huodi.http;


import com.wlqq.huodi.utils.HuoDiConstants;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tiger Tang
 * @since 110612
 *        Date: 11-12-26
 */
public class HttpPostFactory {

    private static boolean isWifi;   // 当前是否为wifi连接

    private static String apnName;   // 如果非wifi连接，当前所使用接入点名称

    public static boolean isWifi() {
        return isWifi;
    }

    public static void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public static String getApnName() {
        return apnName;
    }

    public static void setApnName(String apn) {
        apnName = apn;
    }

    public static HttpPost createHttpPost(String url, Map<String, Object> params, CustomMultiPartEntity.ProgressListener progressListener) throws UnsupportedEncodingException {
        final HttpPost httpPost = new HttpPost(url);
        // 添加Gzip支持
        supportGzip(httpPost);

        // 检查是否需要设置代理
        setProxy(httpPost);

        boolean hasFile = false;

        for (Map.Entry entry : params.entrySet()) {
            final Object value = entry.getValue();

            if (value != null && value instanceof File) {
                hasFile = true;
                break;
            }
        }

        if (hasFile) {
            final MultipartEntity multipartEntity = new CustomMultiPartEntity(progressListener);

            for (String key : params.keySet()) {
                final Object value = params.get(key);
                if (value != null) {
                    if (value instanceof File) {
                        File f = (File) value;
                        multipartEntity.addPart(key, new FileBody(f));
                    } else {
                        multipartEntity.addPart(key, new StringBody(value.toString(), Charset.forName("utf-8")));
                    }
                }
            }

            httpPost.setEntity(multipartEntity);
        } else {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                final Object value = params.get(key);
                if (value != null)
                    parameters.add(new BasicNameValuePair(key, value.toString()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
        }

        return httpPost;
    }

    /**
     * 添加Gzip压缩支持
     */
    private static void supportGzip(HttpRequest request) {
        // 添加对gzip的支持
        request.addHeader("Accept-Encoding", "gzip");
    }

    /**
     * 检查是否设置代理，当前非WIFI连接时，如果接入点为cmwap、uniwap、ctwap和3gwap，则需要设置代理主机地址（cmwap、
     * uniwap和3gwap所用代理地址都10.0.0.172:80，ctwap所用代理地址为10.0.0.200：80 ）
     */
    public static void setProxy(HttpRequest request) {
        if (isWifi)
            return;

        if (ApnNet.CMWAP.equals(apnName) || ApnNet.UNIWAP.equals(apnName)
                || ApnNet.GWAP_3.equals(apnName)) {
            HttpHost proxy = new HttpHost(HuoDiConstants.UNIWAP_PROXY_SERVER, 80);
            ConnRouteParams.setDefaultProxy(request.getParams(), proxy);
        } else if (ApnNet.CTWAP.equals(apnName)) {
            HttpHost proxy = new HttpHost(HuoDiConstants.CTWAP_PROXY_SERVER, 80);
            ConnRouteParams.setDefaultProxy(request.getParams(), proxy);
        }
    }

}
