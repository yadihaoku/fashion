package cn.yyd.kankanshu.utils.net;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.yyd.kankanshu.utils.logging.Ln;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yadi Yan on 2017/4/21.
 */
class ClientWrapper {
    private static final long sCONN_TIMEOUT = 20_000;
    private static final long sCACHE_SIZE = 20 * 1024 * 1024;// 缓存大小 20M
    private OkHttpClient mClient;
    private String mCachePath;

    public ClientWrapper(String path) {
        mCachePath = path;
    }

    public ClientWrapper() {
    }


    OkHttpClient getClient() {
        if (mClient == null) {
            synchronized (this) {
                if (mClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(sCONN_TIMEOUT, TimeUnit.SECONDS);
                    if (!TextUtils.isEmpty(mCachePath))
                        builder.cache(new Cache(new File(mCachePath), sCACHE_SIZE));
                    builder.addInterceptor(new LoggingInterceptor());

                    mClient = builder.build();
                }
            }
        }

        return mClient;
    }

    /**
     * 日志 Application Interceptor 拦截
     */
    static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long startNano = System.nanoTime();
            Ln.i("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());

            Response response = chain.proceed(request);

            long endNano = System.nanoTime();
            Ln.i("Received response for %s in %.1fms%n%s",
                    response.request().url(), (endNano - startNano) / 1e6d, response.headers());

            return response;
        }
    }
}
