package cn.yyd.kankanshu.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.lang.ref.WeakReference;

public class ThreadUtils {

    private static Handler mHandler = null;
    private static final int DEFAULT_DELAY = 2000;

    public static Handler getMainHandler() {
        if (mHandler != null)
            return mHandler;
        synchronized (ThreadUtils.class) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }
        }
        return mHandler;

    }

    public static void runOnUiThread(Runnable run) {
        getMainHandler().post(run);
    }

    public static void runOnUiThread(Runnable run, long delay) {
        getMainHandler().postDelayed(run, delay);
    }

    public static void removeCallback(Runnable callback){
        if(callback == null)return;
        getMainHandler().removeCallbacks(callback);
    }

    public static void delayEnabled(View view){
        delayChangeView(view, View.VISIBLE, true);
    }

    public static void delayChangeView(View view, int status, boolean enabled){
        ThreadUtils.runOnUiThread(new DelayRunnable(view, status, enabled), DEFAULT_DELAY);
    }
    public static void delayChangeView(View view, boolean destEnabled){
        view.setEnabled(!destEnabled);
        ThreadUtils.runOnUiThread(new DelayRunnable(view, destEnabled), DEFAULT_DELAY);
    }

    /**
     * 延迟启用、禁用 View
     */
    static class DelayRunnable implements  Runnable{
        public DelayRunnable(View view, int viewStatus, boolean viewEnabled) {
            viewWeakReference = new WeakReference<>(view);
            this.viewStatus = viewStatus;
            this.viewEnabled = viewEnabled;
        }
        public DelayRunnable(View view, boolean viewEnabled) {
            this(view,-1,viewEnabled);
        }

        private WeakReference<View> viewWeakReference;
        private int viewStatus;
        private boolean viewEnabled;
        @Override
        public void run() {
            View view = viewWeakReference.get();
            if(view != null){
                if(viewStatus != -1)
                    view.setVisibility(viewStatus);
                view.setEnabled(viewEnabled);
            }
        }
    }

    /**
     * 使用延迟加载，在 Activity 中 setContentView 之后，各个元素获取到尺寸后执行该函数
     * @param activity
     */
    public static void runOnViewCreated(Activity activity, final Runnable runnable){
        //使用 lazyLoad 加载数据
        final View decorView = activity.getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                decorView.postDelayed(runnable, 0);
            }
        });
    }
}
