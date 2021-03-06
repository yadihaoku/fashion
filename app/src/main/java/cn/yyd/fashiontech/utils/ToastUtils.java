package cn.yyd.fashiontech.utils;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private static WeakReference<Toast> toastWeakReference;

	public static final void toast(String text) {
        Toast toast;
        if(toastWeakReference != null && (toast = toastWeakReference.get())!=null){
            toast.setText(text);
        }else {
            if (mContext == null) {
                if (mIsTryGetContext)
                    return;
                mContext = getApplicationContext();
                mIsTryGetContext = true;
            }
		   toast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
            toastWeakReference = new WeakReference<Toast>(toast);
        }
        toast.show();
	}

	public static final void permanentToast(String msg) {

		Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
		   toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
		showToast(toast);
	}

	private static final Application getApplicationContext() {
		Class<?> activityThreadClass = null;

		Application context = null;
		try {
			activityThreadClass = Class.forName("android.app.ActivityThread");
			Method method = activityThreadClass.getMethod("currentApplication", (Class[]) null);
			context = (Application) method.invoke(null, (Object[]) null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return context;
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
	
	private static void reflectionTNFunc(Object tn, Toast toast){
		if(mShowMethod !=null) return;
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
