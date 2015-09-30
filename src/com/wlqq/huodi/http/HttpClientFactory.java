package com.wlqq.huodi.http;

import com.mato.sdk.proxy.Address;
import com.mato.sdk.proxy.Proxy;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/**
 * Factory class for creating <code>org.apache.http.client.HttpClient</code> instance.
 *
 * @author Tiger Tang
 * @since 110612
 * Date: 11-12-26
 */
public class HttpClientFactory {

    public static int timeoutConnection = 30000;
    public static int timeoutSocket = 60000;

    private static HttpClient instance = null;

    public static void setTimeoutConnection(int timeoutConnection) {
        HttpClientFactory.timeoutConnection = timeoutConnection;
    }

    public static void setTimeoutSocket(int timeoutSocket) {
        HttpClientFactory.timeoutSocket = timeoutSocket;
    }

    public static HttpClient createHttpClient() {
        if (instance == null) {
            BasicHttpParams params = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(params, timeoutConnection);
            HttpConnectionParams.setSoTimeout(params, timeoutSocket);

            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
            schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
            instance = new DefaultHttpClient(cm, params);

            Address address = Proxy.getAddress();
            if (address != null) {
                HttpHost proxyHost =
                        new HttpHost(address.getHost(),
                                address.getPort());
                instance.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxyHost);
            }
        }
        return instance;

    }


    public static void setProxy(String host, int port, String schema) {
        createHttpClient().getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, new HttpHost(host, port, schema));
    }
}
