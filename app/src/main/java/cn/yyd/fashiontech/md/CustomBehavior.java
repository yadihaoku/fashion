package cn.yyd.fashiontech.md;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import java.util.List;

/**
 * Created by YanYadi on 2016/12/21.
 * 自定义 Behavior
 */
public class CustomBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "CustomBehavior";

    /**
     * Default constructor for instantiating Behaviors.
     */
    public CustomBehavior() {
    }

    /**
     * Default constructor for inflating Behaviors from layout. The Behavior will have
     * the opportunity to parse specially defined layout parameters. These parameters will
     * appear on the child view tag.
     *
     * @param context
     * @param attrs
     */
    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 是否依赖于指定的一个 兄弟结点 view
     *
     * @param parent     是 CoordinatorLayout
     * @param child      当前View
     * @param dependency 兄弟节点
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "layoutDependsOn: view=" + dependency);
//        return dependency instanceof AppBarLayout;
        return false;
    }

    /**
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "onDependentViewChanged: " + (child == dependency));
        offsetChildAsNeeded(parent, child, dependency);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            // Offset the child, pinning it to the bottom the header-dependency, maintaining
            // any vertical gap and overlap
            ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop()));
//            child.offsetTopAndBottom(250);
        }
    }

//
//    @Override
//    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//        Log.i(TAG, "onLayoutChild: child =" + child);
//
//        layoutChild(parent, child, layoutDirection);
//
//        return true; // super.onLayoutChild(parent, child, layoutDirection);
//    }
//
//    protected void layoutChild1(CoordinatorLayout parent, View child, int layoutDirection) {
//        // Let the parent lay it out by default
//        AppBarLayout ablLayout = findFirstDependency(parent.getDependencies(child));
//        parent.onLayoutChild(child, layoutDirection);
//    }

    AppBarLayout findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof AppBarLayout) {
                return (AppBarLayout) view;
            }
        }
        return null;
    }


//    protected void layoutChild(final CoordinatorLayout parent, final View child,
//                               final int layoutDirection) {
//        final List<View> dependencies = parent.getDependencies(child);
//        final View header = findFirstDependency(dependencies);
//
//        if (header != null) {
//            final CoordinatorLayout.LayoutParams lp =
//                    (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//            final Rect available = new Rect();
//            available.set(parent.getPaddingLeft() + lp.leftMargin,
//                    header.getBottom() + lp.topMargin,
//                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
//                    parent.getHeight() + header.getBottom()
//                            - parent.getPaddingBottom() - lp.bottomMargin);
//
//
//            final Rect out = new Rect();
//            GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(),
//                    child.getMeasuredHeight(), available, out, layoutDirection);
//
//            final int overlap = 0;
//
//            child.layout(out.left, out.top - overlap, out.right, out.bottom - overlap);
//        } else {
//            // If we don't have a dependency, let other method handle it
//            layoutChild1(parent, child, layoutDirection);
//        }
//    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.i(TAG, "onLayoutChild: child = " + child);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
//        if (lp.dodgeInsetEdges == Gravity.NO_GRAVITY) {
//            // If the developer hasn't set dodgeInsetEdges, lets set it to BOTTOM so that
//            // we dodge any Snackbars
//            lp.dodgeInsetEdges = Gravity.BOTTOM;
//        }
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        Log.i(TAG, "onNestedPreScroll: ");
        Log.i(TAG, "onNestedPreScroll: ================PreScroll dy " + dy);
        if (dy != 0) {
            //向下划 显示
            if (dy < 0) {
                Log.i(TAG, "onNestedPreScroll: " + target.canScrollVertically(-1));
                Log.i(TAG, "onNestedPreScroll: "+ target.getScrollY());
                //如下向下滚动，并且已经到顶。则显示
                if (! target.canScrollVertically(-1) && child.getVisibility() != View.VISIBLE) {
                    child.setVisibility(View.VISIBLE);
                }
            } else {
                child.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: target = " + target);
        Log.i(TAG, "onNestedScroll: dy = "+ dyConsumed);
        Log.i(TAG, "onNestedScroll: canVerticalScroll =" + target.canScrollVertically(-1));
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll: target=" + target);


        // Return true if we're nested scrolling vertically, and we have scrollable children
        // and the scrolling view is big enough to scroll
        final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;

//                    && parent.getHeight() - directTargetChild.getHeight() <= child.getHeight();


        return true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        Log.i(TAG, "onStopNestedScroll: target==" + target);
        Log.i(TAG, "onStopNestedScroll: verticalY " + target.canScrollVertically(-1));
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
