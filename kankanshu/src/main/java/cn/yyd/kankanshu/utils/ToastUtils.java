package cn.yyd.kankanshu.utils;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static cn.yyd.kankanshu.utils.ApplicationUtils.getApplicationContext;

/**
 * 显示 Toast 消息
 */
public class ToastUtils {
    private static Application mContext;
    private static boolean mIsTryGetContext = false;
    private static Field mTnField;
    private static Method mShowMethod;
    private static Method mHideMethod;
    private static Field mTnNextView;

    public static final void toast(String text) {
        if (mContext == null) {
            if (mIsTryGetContext)
                throw new RuntimeException("Can not reflect Application Context.");
            mContext = getApplicationContext();
            mIsTryGetContext = true;
        }
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    public static final void permanentToast(String msg) {

        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        showToast(toast);
    }
    private static void showToast(Toast toast) {
        try {
            reflectionTN();
            Object tnObj = mTnField.get(toast);
            reflectionTNFunc(tnObj, toast);
            mTnNextView.set(tnObj, toast.getView());
            mShowMethod.invoke(tnObj);// 调用TN对象的show()方法，显示toast
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void reflectionTN() {
        if (mTnField != null)
            return;
        try {
            Class<Toast> toastClazz = Toast.class;
            mTnField = toastClazz.getDeclaredField("mTN");
            mTnField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void reflectionTNFunc(Object tn, Toast toast) {
        if (mShowMethod != null) return;
        try {
            Class<?> toastClazz = tn.getClass();
            mShowMethod = toastClazz.getDeclaredMethod("show");

            mTnNextView = toastClazz.getDeclaredField("mNextView");
            mTnNextView.setAccessible(true);


//			System.out.println(toastClazz);
//			System.out.println(mShowMethod);
            mShowMethod.setAccessible(true);
            mHideMethod = toastClazz.getDeclaredMethod("hide");
            mHideMethod.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
