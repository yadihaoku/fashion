package cn.yyd.kankanshu.utils;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Checkable;

/**
 * Created by YanYadi on 2017/6/20.
 */
public class ViewUtils {

    public final static void checkedItem(Checkable view, ViewParent parent) {
        //如果当前view已经选中，立即返回
        if (view.isChecked()) return;
        final ViewGroup viewGroup = (ViewGroup) parent;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            Checkable item = (Checkable) viewGroup.getChildAt(i);
            item.setChecked(item == view);
        }
    }
}
