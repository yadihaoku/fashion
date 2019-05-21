package cn.yyd.kankanshu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.utils.logging.Ln;

/**
 * Created by YanYadi on 2017/6/23.
 */
public class LoadingTextView extends View {
    private Bitmap mLoading;
    private int mBmpWidth, mBmpHeight;

    private Paint mLayerPaint;
    //源图绘制区域
    private Rect mSrcRect;
    //目标绘制区域
    private Rect mDstRect;
    //渐变层 区域
    private RectF mXfermodeRect;

    private int mLayerEnd, mLayerStart, mCurrentTop, mViewWidth, mViewHeight;

    private Xfermode xfermode;

    private ValueAnimator mAnim;
    public LoadingTextView(Context context) {
        this(context, null);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLoading = ((BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.ic_anim_loading)).getBitmap();
        mBmpHeight = mLoading.getHeight();
        mBmpWidth = mLoading.getWidth();

        mLayerPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        //是否过滤 bitmap ，主要作用是抗锯齿
        mLayerPaint.setFilterBitmap(true);
        mLayerPaint.setColor(Color.RED);
        //设置防抖动
        mLayerPaint.setDither(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mLayerPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        Ln.d("onMeasure mode = %d , isUnspecified = %s", widthMeasureMode , (widthMeasureMode == MeasureSpec.UNSPECIFIED));

        Ln.d("size " + MeasureSpec.getSize(heightMeasureSpec));

        //未指定大小，使用 bitmap 的原始尺寸
        if (widthMeasureMode != MeasureSpec.EXACTLY && mBmpWidth != 0) {
            setMeasuredDimension(mBmpWidth, mBmpHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void animUseAnimator() {
        Ln.d("animUseAnimator...");
        if(mAnim != null){
            mAnim.cancel();
        }

        //使用 valueAnimator 无需使用继承，直接初始化，填入 updateListener 即可 。
        mAnim = ValueAnimator.ofInt(mLayerStart, mLayerEnd);
        mAnim.setRepeatMode(ValueAnimator.REVERSE);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                final int animTop = (int) animation.getAnimatedValue();
                mXfermodeRect.top = animTop;
                invalidate();
            }
        });
        mAnim.setDuration(2000);
        mAnim.start();
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        animUseAnimator();
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mAnim != null){
            mAnim.cancel();
            mAnim = null;
        }
    }

    @Override public void onDraw(Canvas canvas) {
        //保存新图层
        final int saveCount = canvas.saveLayer(0, 0, mViewWidth, mViewHeight, mLayerPaint, Canvas.ALL_SAVE_FLAG);
        //绘制原始图像
        canvas.drawBitmap(mLoading, mSrcRect, mDstRect, mLayerPaint);
        //设置混合模式
        mLayerPaint.setXfermode(xfermode);
        //绘制混合颜色
        mLayerPaint.setColor(Color.RED);
        canvas.drawRect(mXfermodeRect, mLayerPaint);
        //取消混合模式
        mLayerPaint.setXfermode(null);
        //合并图层
        canvas.restoreToCount(saveCount);

    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //源图区域
        mSrcRect = new Rect(0, 0, mBmpWidth, mBmpHeight);
        //内部边距 4 dp
        final int innerMargin = TypedValue.complexToDimensionPixelOffset(4, getResources().getDisplayMetrics());
        //目标区域
        mDstRect = new Rect(innerMargin, innerMargin, w - innerMargin, h - innerMargin);
        //开始位置 为 底部
        mLayerStart = h - innerMargin;
        mCurrentTop = mLayerStart;
        //结束位置 为 顶部
        mLayerEnd = innerMargin;
        //特效层初始化
        mXfermodeRect = new RectF(innerMargin, mLayerStart, mSrcRect.right, mLayerStart);

        mViewHeight = h;
        mViewWidth = w;

        animUseAnimator();
    }
}
