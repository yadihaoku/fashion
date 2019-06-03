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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YanYadi on 2017/5/24.
 */
public class HttpClientTest {
    static final String S_12306 = "https://kyfw.12306.cn/otn/";

    public static void main(String[] args) throws IOException {
        final Thread thread = Thread.currentThread();
        OkHttpUtils.init("E:\\cache");
        OkHttpClient trustClient = OkHttpUtils.getHttpClient().newBuilder().sslSocketFactory(sslSocketFactory()).build();
        trustClient.newCall(new Request.Builder().url(S_12306).get().build()).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                System.out.println("=======================");
                peekThread();
                if(thread != Thread.currentThread())
                    System.out.println("synchronize");
                System.out.println("=======================");
            }
        });
        peekThread();
        System.out.println("------------------");
    }
    static void peekThread(){
        System.out.println(Thread.currentThread().getThreadGroup());
        System.out.println(Thread.currentThread().getName());
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

            //应该执行默认验证
            //验证不通过，抛出异常
            x509Certificates[0 ].checkValidity();
        }

        @Override public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
