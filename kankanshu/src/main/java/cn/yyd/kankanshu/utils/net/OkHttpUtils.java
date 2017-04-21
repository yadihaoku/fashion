package cn.yyd.kankanshu.utils.net;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by YanYadi on 2017/4/21.
 */
public class OkHttpUtils {

    private static ClientWrapper mClientWrapper;

    public static final void init(Context context) {
        if (mClientWrapper == null)
            synchronized (OkHttpUtils.class) {
                if (mClientWrapper == null)
                    mClientWrapper = new ClientWrapper(context.getCacheDir().getAbsolutePath());
            }
    }

    /**
     * 请先调用  {@link OkHttpUtils#init(Context)}
     * @return
     */
    public final static OkHttpClient getHttpClient() {
        return mClientWrapper.getClient();
    }
}
