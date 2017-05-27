package com.example.okhttp;

import com.example.spider.net.OkHttpUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YanYadi on 2017/5/24.
 */
public class HttpClientTest {
    static final String S_12306 = "https://kyfw.12306.cn/otn/";

    public static void main(String[] args) throws IOException {
        OkHttpUtils.init("E:\\cache");
        OkHttpClient trustClient = OkHttpUtils.getHttpClient().newBuilder().sslSocketFactory(sslSocketFactory()).build();
        Response response = trustClient.newCall(new Request.Builder().url(S_12306).get().build()).execute();
        System.out.println(response.body().string());
    }

    static SSLSocketFactory sslSocketFactory() {
        TrustManager trustManagers[] = new TrustManager[1];
        trustManagers[0] = new TrustAllHostManager();
        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sslContext.getSocketFactory();
    }

    static class TrustAllHostManager implements X509TrustManager {

        @Override public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            System.out.println("checkClientTrusted " + x509Certificates);
            System.out.println(s);
        }

        @Override public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            System.out.println("checkServerTrusted " + x509Certificates);
            System.out.println(x509Certificates.length);
            System.out.println(x509Certificates[0]);
            System.out.println(s);
        }

        @Override public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
