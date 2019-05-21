package cn.yyd.fashiontech.anim;

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

import cn.yyd.fashiontech.R;

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

    public LoadingTextView(Context context) {
        this(context, null);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLoading = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.ic_anim_loading)).getBitmap();
        mBmpHeight = mLoading.getHeight();
        mBmpWidth = mLoading.getWidth();

        mLayerPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        //是否过滤 bitmap ，主要作用是抗锯齿
        mLayerPaint.setFilterBitmap(true);
        mLayerPaint.setColor(Color.RED);
        //设置防抖动
        mLayerPaint.setDither(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mLayerPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16, getResources().getDisplayMetrics()));

    }

    @Override protected void onDraw(Canvas canvas) {
        //---测试1，在 saveLayer 之前，绘制一段绿色文字
        //该段文字，如果在使用 saveLayer之后，设置 xfermode ，文字颜色不受影响。
        // 如果在使用 save 之后，再设置 xfermode ，文字颜色 受影响。
        // saveLayer \ save 的区别就在此
        mLayerPaint.setColor(Color.GREEN);
        canvas.drawText("绿色", 0,mViewHeight/2,mLayerPaint);
        //保存新图层
//        final int saveCount = canvas.save(); //canvas.saveLayer(0, 0, mViewWidth, mViewHeight, mLayerPaint, Canvas.ALL_SAVE_FLAG);
        final int saveCount = canvas.saveLayer(0, 0, mViewWidth, mViewHeight, mLayerPaint, Canvas.ALL_SAVE_FLAG);

        //再绘制一段蓝色文字
        //该段文字受 xfermode 影响，慢慢变成了红色
        mLayerPaint.setColor(Color.BLUE);
        canvas.drawText("蓝色", 0, 30, mLayerPaint);

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

        //再绘制一段黑色文字
        mLayerPaint.setColor(Color.BLACK);
        canvas.drawText("黑色", 0, mViewHeight -10, mLayerPaint);

        if (mCurrentTop <= mLayerEnd)
            mCurrentTop = mLayerStart;
        mCurrentTop -= 1;
        mXfermodeRect.top = mCurrentTop;
        postInvalidate();
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
    }
}
