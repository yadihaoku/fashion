package cn.yyd.kankanshu.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import cn.yyd.kankanshu.R;

/**
 * Created by YanYadi on 2017/6/26.
 */
public class Loading extends Dialog {
    public Loading(@NonNull Context context) {
        super(context);
    }

    public Loading(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected Loading(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        //设置坐标偏移量
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.x = 100;
//        layoutParams.y = 100 ;
//        getWindow().setAttributes(layoutParams);
        //设置背景不透明度
        getWindow().setDimAmount(0.3f);
        //清空 background
        getWindow().getDecorView().setBackground(null);
    }

    public static Loading show(Context context) {
        Loading loading = new Loading(context, true, null);
        return loading;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.widget_dialog_loading);
    }
}
