package cn.yyd.fashiontech;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import cn.yyd.fashiontech.anim.CubicAnimActivity;
import cn.yyd.fashiontech.gesture.GestureActivity;
import cn.yyd.fashiontech.md.MD_TextInputLayout_2;
import cn.yyd.fashiontech.md.RecyclerActivity;
import cn.yyd.fashiontech.utils.ToastUtils;
import cn.yyd.fashiontech.window.WindowActivity;

/**
 * Created by YanYadi on 2016/10/28.
 */
public class MainActivityHandler {

    private static final String TAG = "MainActivityHandler";
    public void onTitleClick(final View view) {
        ToastUtils.toast("点击 TextView ");

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            private int idleInvokeCount = 0;

            @Override
            public boolean queueIdle() {

                Log.i("queueIdle", "queueIdle: " + idleInvokeCount);
                if (idleInvokeCount == 9)
                    startLooper(view);
                return idleInvokeCount++ < 9;
            }
        });
    }

    /**
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onChangeBgClick(final View view){
        ValueAnimator valueAnimator = ObjectAnimator.ofArgb(view,"backgroundColor", 0xFFFF0000, 0xFF0000FF);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    public void toAnimator(View view){
        view.getContext().startActivity(new Intent(view.getContext(), CubicAnimActivity.class));
    }
    public void toInputLayout(View view){
        view.getContext().startActivity(new Intent(view.getContext(), MD_TextInputLayout_2.class));
    }
    public void openWindow(View view){
        view.getContext().startActivity(new Intent(view.getContext(), WindowActivity.class));

    }

    public void toRecycler(View view){
        Log.i(TAG, "toRecycler: ");
        view.getContext().startActivity(new Intent(view.getContext(), RecyclerActivity.class));
    }

    public void startLooper(final View view) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.toast(view + "thread " + Thread.currentThread().getName());
                    }
                });
                Looper.loop();
            }
        }.start();
    }

    public void toGesture(View view){
        view.getContext().startActivity(new Intent(view.getContext(), GestureActivity.class));
    }

    public void vibrator(View view){
        final Vibrator vibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{100,100,50,200,500,50},0);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                vibrator.cancel();
            }
        },1000_0);
    }

    public void parallax(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ParallaxToolBar.class));
    }
    public void showRecycler(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ParallaxToolBar.class));
    }
}
