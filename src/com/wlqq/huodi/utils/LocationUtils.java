package com.wlqq.huodi.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xlw
 *         Date: 12-6-13
 */
public class LocationUtils {
    private static final String TAG = "LocationUtils";

    /**
     * 根据城市名称获取经纬度
     *
     * @param city 城市中文名称、拼音、英文
     * @return location
     *         经纬度数组,0为经度,1为纬度
     */
    public static int[] getLocationByCityName(String city) {
        int[] location = new int[2];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(
                    "http://maps.google.com/maps/geo?q=" + city);
            int res;
            res = httpClient.execute(httpPost).getStatusLine()
                    .getStatusCode();
            if (res == 200) {
                /*
                * 当返回码为200时，做处理 得到服务器端返回json数据，并做处理
                */
                HttpResponse httpResponse = httpClient
                        .execute(httpPost);
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader2 = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity()
                                .getContent()));
                for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
                        .readLine()) {
                    builder.append(s);
                }
                /**
                 * 这里需要分析服务器回传的json格式数据，
                 */
                JSONObject jsonObject = new JSONObject(builder
                        .toString());
                JSONArray jsonArray = jsonObject
                        .getJSONArray("Placemark");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = (JSONObject) jsonArray
                            .opt(i);
                    JSONObject jsonObject3 = new JSONObject(jsonObject2
                            .getString("Point"));
                    JSONArray jsonArray1 = jsonObject3
                            .getJSONArray("coordinates");
                    location[0] = (int) ((Float.parseFloat(jsonArray1.get(0).toString())) * 1E6);
                    location[1] = (int) ((Float.parseFloat(jsonArray1.get(1).toString())) * 1E6);
                }
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return location;
    }

}
