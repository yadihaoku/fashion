package cn.yyd.kankanshu.utils;

import android.graphics.Paint;

/**
 * Created by YanYadi on 2017/4/6.
 */

public class TextUtils {

    public static float getTextHeight(Paint.FontMetrics fm){

        return  (float)Math.ceil(fm.descent - fm.ascent);

    }
}
