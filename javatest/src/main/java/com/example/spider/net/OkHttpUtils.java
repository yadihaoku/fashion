package com.example.spider.net;

import okhttp3.OkHttpClient;

/**
 * Created by YanYadi on 2017/4/21.
 */
public class OkHttpUtils {

    private static ClientWrapper mClientWrapper;

    public static final void init(String cacheDir) {
        if (mClientWrapper == null)
            synchronized (OkHttpUtils.class) {
                if (mClientWrapper == null)
                    mClientWrapper = new ClientWrapper(cacheDir);
            }
    }

    /**
     * @return
     */
    public final static OkHttpClient getHttpClient() {
        return mClientWrapper.getClient();
    }
}
