package cn.yyd.kankanshu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by YanYadi on 2017/6/21.
 */
public class AnchoredListView extends ListView {
    public AnchoredListView(Context context) {
        this(context, null);
    }

    public AnchoredListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnchoredListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnchoredListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
