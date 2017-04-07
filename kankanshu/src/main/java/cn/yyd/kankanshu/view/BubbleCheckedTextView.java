package cn.yyd.kankanshu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CheckedTextView;

import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.utils.TextUtils;

/**
 * Created by YanYadi on 2017/4/6.
 */

public class BubbleCheckedTextView extends CheckedTextView {

    private String mBubbleText;
    private int mBubbleColor;
    private int mBubbleTxtColor;
    private int mStrokeColor;
    private float mTxtWidth;

    private int mChangeStatus;

    private final int sBUBBLE_TEXT_CHANGED = 1;

    private int mWidth ;

    private TextPaint mTxtPaint;
    private int mBubblePadding;
    private RectF mBubbleRect;
    public BubbleCheckedTextView(Context context) {
        this(context, null);
    }

    public BubbleCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BubbleCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BubbleCheckedTextView, defStyleAttr, defStyleRes);
        mBubbleColor = array.getColor(R.styleable.BubbleCheckedTextView_bubbleColor, Color.RED);
        mBubbleTxtColor = array.getColor(R.styleable.BubbleCheckedTextView_bubbleTextColor, Color.WHITE);
        setBubbleText((String)array.getText(R.styleable.BubbleCheckedTextView_bubbleText));
        mTxtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mBubblePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mStrokeColor = array.getColor(R.styleable.BubbleCheckedTextView_bubbleStrokeColor,Color.BLUE);

        array.recycle();
    }

    public void setBubbleText(String text){
        mBubbleText = text;
        mChangeStatus |= sBUBBLE_TEXT_CHANGED;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(android.text.TextUtils.isEmpty(mBubbleText))return;
        if( (mChangeStatus & sBUBBLE_TEXT_CHANGED) != 0) {
            mChangeStatus ^= sBUBBLE_TEXT_CHANGED;
            mTxtWidth = mTxtPaint.measureText(mBubbleText);

            float mTxtHeight = TextUtils.getTextHeight(mTxtPaint.getFontMetrics());
            int halfWidth = mWidth / 2;
            float l;
            float bubbleWidth = mTxtWidth + mBubblePadding * 2;
            if(halfWidth > bubbleWidth){
                l = halfWidth;
            }else{
                l = (mWidth - bubbleWidth);
            }
            if(mBubbleRect == null)
                mBubbleRect = new RectF();
            int t = mBubblePadding;
            mBubbleRect.set(l,t,l + bubbleWidth, t + mTxtHeight);
        }
        canvas.save();
        // 画背景
        mTxtPaint.setColor(mBubbleColor);
        mTxtPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(mBubbleRect, mBubbleRect.height() /2,mBubbleRect.height() /2, mTxtPaint);
        // 画描边
        mTxtPaint.setStyle(Paint.Style.STROKE);
        mTxtPaint.setColor(mStrokeColor);
        mTxtPaint.setStrokeWidth(1);
        canvas.drawRoundRect(mBubbleRect, mBubbleRect.height() /2,mBubbleRect.height() /2, mTxtPaint);
        //画文本
        mTxtPaint.setStyle(Paint.Style.FILL);
        mTxtPaint.setColor(mBubbleTxtColor);
        canvas.drawText(mBubbleText,mBubbleRect.left + mBubblePadding, mBubbleRect.bottom - mTxtPaint.getFontMetrics().descent, mTxtPaint);

        canvas.restore();

    }
}
