package cn.yyd.kankanshu.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by YanYadi on 2017/8/1.
 */
public class PullToReturnView extends FrameLayout {
    private static final String TAG = "PullToReturnView";
    private TextView mTitleView;
    private View mContentView;
    private int mLayoutTop;
    private int mTouchSlop;
    private int mMotionX, mMotionY, mLastX, mLastY;
    private boolean mIsBeginDrag;
    //开始横向滑动
    private boolean mIsBeginHorizontalScroll;
    private int mPreTop = -1;
    private int mActivePointerId;
    /**
     * 最大可滑动高度
     */
    private int sMAX_TOP;
    /**
     * 触发回弹事件的，最大滑动距离比例。
     */
    private float mCanReturnDistanceRatio = 2 / 3f;

    public PullToReturnView(@NonNull Context context) {
        super(context);
        init();
    }

    public PullToReturnView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToReturnView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public PullToReturnView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mTitleView = makeInnerView();
        //最大可滑动高度
        sMAX_TOP = (int) (getResources().getDisplayMetrics().heightPixels / 5);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        addView(mTitleView, 0, mTitleView.getLayoutParams());
    }

    private TextView makeInnerView() {
        TextView textView = new TextView(getContext());
        textView.setText("释放返回商品详情");
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER);
        final int padding = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()) + 0.5);
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setPadding(0, padding, 0, padding);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (mContentView != null)
            throw new RuntimeException("PullToReturnView 只能有一个子view .");
        if (child != mTitleView) {
            mContentView = child;
        }
        super.addView(child, index, params);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: ---> ");
        final int actionMasked = ev.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                handleActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!handleActionMove(ev))
                    return false;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
        }
        return mIsBeginDrag;
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ----> " + mLayoutTop);
        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_MOVE:
                if (!handleActionMove(event))
                    return false;
                break;
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                handleActionDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                handlePointerDown(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handleActionUp(event);
                break;
        }
        return true;// super.onTouchEvent(event);
    }

    private void handleActionUp(MotionEvent event) {
        if (mLayoutTop == 0) return;
        //滑动距离，已经大于 可以滑动最低比例
        //// TODO: 2017/8/2 触发回弹事件
        if (mLayoutTop > sMAX_TOP * mCanReturnDistanceRatio) {

        }
        springBack(mLayoutTop);

    }

    /**
     * 恢复原位
     *
     * @param distance
     */
    private void springBack(int distance) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(distance, 0);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                final int currentDistance = (int)  animation.getAnimatedValue();
                mLayoutTop = currentDistance;
                layoutChildren();
            }
        });
        valueAnimator.start();
    }

    private void handlePointerDown(MotionEvent event) {
        //设置新的手指，为当前坐标的获取索引
        //由该手指处理以后的事件
        final int pointerIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(pointerIndex);
        mActivePointerId = pointerId;
        mMotionX = (int) (event.getX(pointerIndex) + 0.5);
        mMotionY = (int) (event.getY(pointerIndex) + 0.5);
        mLastY = mMotionY;
        mLastX = mMotionX;
    }

    private void log() {
        //   System.out.println(" motionX="+ mMotionY + " motionY="+ mMotionY + " mLastX="+ mLastX +" mLastY=" + mLastY + " mIsDrag="+ mIsBeginDrag + " mIsScroll="+ mIsBeginHorizontalScroll);
    }

    private boolean handleActionMove(MotionEvent event) {
        log();
        if (mIsBeginHorizontalScroll) return false;
        final int usedIndex = event.findPointerIndex(mActivePointerId);
        //手指已经丢失，不再处理后续事件
        if (usedIndex < 0) {
            return false;
        }
        mLastX = (int) (event.getX(usedIndex) + 0.5);
        mLastY = (int) (event.getY(usedIndex) + 0.5);
        if (mIsBeginDrag) {
            mLayoutTop += (int) ((mLastY - mMotionY) * 0.7 + 0.5);
            mLayoutTop = mLayoutTop < 0 ? 0 : mLayoutTop;
            if (mLayoutTop > sMAX_TOP)
                mLayoutTop = sMAX_TOP;
            mMotionY = mLastY;
            layoutChildren();
        } else {
            //开始横向滑动，放弃此次事件
            if (!mIsBeginDrag && Math.abs(mLastX - mMotionX) > mTouchSlop) {
                mIsBeginHorizontalScroll = true;
                return false;
            }
            if (Math.abs(mLastY - mMotionY) > mTouchSlop) {
                mIsBeginDrag = true;
                mMotionY = mLastY;
            }
        }
        return true;
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (MotionEventCompat.getPointerId(e, actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(e, newIndex);
            mMotionX = mLastX = (int) (MotionEventCompat.getX(e, newIndex) + 0.5f);
            mMotionY = mLastY = (int) (MotionEventCompat.getY(e, newIndex) + 0.5f);
        }
    }


    private void handleActionDown(MotionEvent event) {
        mIsBeginDrag = false;
        mIsBeginHorizontalScroll = false;
        mMotionX = (int) event.getX();
        mMotionY = (int) event.getY();
        mLastX = mMotionX;
        mLastY = mMotionY;
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChildren();
    }

    private void layoutChildren() {
        if (mPreTop != mLayoutTop) {
            invalidate();
            System.out.println("请求重绘");
            mPreTop = mLayoutTop;
        }
        final int titleViewHeight = mTitleView.getMeasuredHeight();
        final int titleViewTop = -titleViewHeight + mLayoutTop;
        final int titleViewBottom = mLayoutTop;
        mTitleView.layout(0, titleViewTop, mTitleView.getMeasuredWidth(), titleViewBottom);
        mContentView.layout(0, titleViewBottom, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight() + titleViewBottom);
        System.out.println("------> measureHeight " + mContentView.getMeasuredHeight());
    }

}
