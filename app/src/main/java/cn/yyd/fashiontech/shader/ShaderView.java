package cn.yyd.fashiontech.shader;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.io.InputStream;

import cn.yyd.fashiontech.R;

/**
 * Created by YanYadi on 2016/12/26.
 */
public class ShaderView extends View {
    private static final String TAG = "ShaderView";
    private Bitmap mBmpAvatar;
    private Context mContext;

    private Paint mPaint;

    private int mTargetDensity;

    private int mBitmapWidth, mBitmapHeight;

    private static final int[] VIEW_IMAGE_RESOURCES = {R.attr.shader_src};

    private BitmapShader mShader;
    private Path mTriangle;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, VIEW_IMAGE_RESOURCES, defStyleAttr, 0);
        TypedValue typedValue = new TypedValue();
        typedArray.getValue(0, typedValue);

        Log.i(TAG, "ShaderView: TypeValue.density =" + typedValue.density);
        initDrawable(R.mipmap.ic_avatar, typedValue.density);
        setWillNotDraw(false);

        typedArray.recycle();
    }

    private void initShader() {
        mShader = new BitmapShader(mBmpAvatar, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
    }

    private void initDrawable(final int resId, final int typeValDensity) {
        Rect outSize = new Rect();
        Resources resources = getResources();
        final TypedValue value = new TypedValue();
        InputStream is = resources.openRawResource(resId, value);
        BitmapFactory.Options opts;
        opts = new BitmapFactory.Options();
        opts.inScreenDensity = resources != null ? resources.getDisplayMetrics().densityDpi : DisplayMetrics.DENSITY_DEFAULT;

        final int density = typeValDensity;
        //如果解析出的  TypedValue.density 是 TypedValue.DENSITY_DEFAULT 。则取
        //DisplayMetrics.DENSITY_DEFAULT 为默认密度。
        if (density == TypedValue.DENSITY_DEFAULT) {
            opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        } else if (density != TypedValue.DENSITY_NONE) {
            opts.inDensity = density;
        }

        opts.inTargetDensity = resources.getDisplayMetrics().densityDpi;
        Log.i(TAG, "initDrawable: Default =" + DisplayMetrics.DENSITY_DEFAULT);
        Log.i(TAG, "initDrawable: resourceDensity =" + resources.getDisplayMetrics().densityDpi);
        Log.i(TAG, "initDrawable: inScreenDensity=" + opts.inScreenDensity + "  inDensity=" + opts.inDensity);
        Log.i(TAG, "initDrawable: inTargetDensity=" + opts.inTargetDensity);

        mBmpAvatar = BitmapFactory.decodeStream(is, outSize, opts);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(48);

        Log.i(TAG, "initDrawable: " + outSize);

        updateLocalState(resources);
        if (mBmpAvatar != null)
            initShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mBmpAvatar, 0f, 0f, mPaint);
        canvas.drawPath(mTriangle, mPaint);

//        canvas.drawText("这是毛线 ", 10,50, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTriangle == null) {
            mTriangle = new Path();
        } else {
            mTriangle.reset();
        }
        //移动到顶点
        mTriangle.moveTo(0, 0);
        //左边点
        mTriangle.lineTo(0, h);
        //右边点
        mTriangle.lineTo(w, h);
//        mTriangle.lineTo(w / 2, 0);
        mTriangle.close();
    }

    private void updateLocalState(Resources res) {
        if (res != null) {
            final int densityDpi = res.getDisplayMetrics().densityDpi;
            mTargetDensity = densityDpi == 0 ? DisplayMetrics.DENSITY_DEFAULT : densityDpi;
        } else {
            mTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        }
        computeBitmapSize();
    }

    private void computeBitmapSize() {
        final Bitmap bitmap = mBmpAvatar;
        if (bitmap != null) {
            mBitmapWidth = bitmap.getScaledWidth(mTargetDensity);
            mBitmapHeight = bitmap.getScaledHeight(mTargetDensity);
        } else {
            mBitmapWidth = mBitmapHeight = -1;
        }

        Log.i(TAG, "computeBitmapSize: size =" + (mBitmapWidth + "x" + mBitmapHeight));
    }
}
