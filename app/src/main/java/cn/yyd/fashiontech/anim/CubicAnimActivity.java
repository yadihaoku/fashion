package cn.yyd.fashiontech.anim;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.yyd.fashiontech.R;

public class CubicAnimActivity extends AppCompatActivity {


    private TextView mTvShopCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubic_anim);

        mTvShopCar = (TextView) findViewById(R.id.tv_car);
        findViewById(R.id.btn_add_to_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartAnim(v);
            }
        });
    }

    private void doStartAnim(View btn) {

        View circle = createCircleView();
        circle.setLayoutParams(makeCircleLP(btn));

        ((ViewGroup)getWindow().getDecorView()).addView(circle);
        circle.bringToFront();

        startAnimator(btn, mTvShopCar, circle);
    }

    private void startAnimator(View source, final View target, final View animView){
        final PathMeasure pathMeasure = new PathMeasure();
        int [] sourcePos = new int[2];
        int [] targetPos = new int[2];
        int [] cubicPos = new int[2];
        source.getLocationInWindow(sourcePos);
        target.getLocationInWindow(targetPos);
        cubicPos[0] = targetPos[0];
        cubicPos[1] = sourcePos[1];

        Path animPath = new Path();
        animPath.moveTo(sourcePos[0], sourcePos[1]);
        animPath.quadTo(cubicPos[0],cubicPos[1], targetPos[0],targetPos[1]);
//        animPath.cubicTo(sourcePos[0],sourcePos[1], cubicPos[0],cubicPos[1], targetPos[0],targetPos[1]);
        pathMeasure.setPath(animPath, false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());

        valueAnimator.addUpdateListener(new  ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] mCurrentPosition = new float[2];
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, mCurrentPosition, null);
                ((FrameLayout.LayoutParams)animView.getLayoutParams()).topMargin = (int)mCurrentPosition[1];
                ((FrameLayout.LayoutParams)animView.getLayoutParams()).leftMargin = (int)mCurrentPosition[0];
//                animView.requestLayout();
                animView.requestLayout();

//                animView.setTranslationX(mCurrentPosition[0]);
//                animView.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();
    }
    private void startAnimatorByTranslation(View source, final View target, final View animView){
        final PathMeasure pathMeasure = new PathMeasure();
        int [] sourcePos = new int[2];
        int [] targetPos = new int[2];
        int [] cubicPos = new int[2];
        source.getLocationInWindow(sourcePos);
        target.getLocationInWindow(targetPos);
        cubicPos[0] = targetPos[0];
        cubicPos[1] = sourcePos[1];

        Path animPath = new Path();
        animPath.moveTo(sourcePos[0], sourcePos[1]);
        animPath.quadTo(cubicPos[0],cubicPos[1], targetPos[0],targetPos[1]);
//        animPath.cubicTo(sourcePos[0],sourcePos[1], cubicPos[0],cubicPos[1], targetPos[0],targetPos[1]);
        pathMeasure.setPath(animPath, false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());

        valueAnimator.addUpdateListener(new  ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] mCurrentPosition = new float[2];
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, mCurrentPosition, null);
                ((FrameLayout.LayoutParams)animView.getLayoutParams()).topMargin = (int)mCurrentPosition[1];
                ((FrameLayout.LayoutParams)animView.getLayoutParams()).leftMargin = (int)mCurrentPosition[0];
                animView.requestLayout();

//                animView.setTranslationX(mCurrentPosition[0]);
//                animView.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();
    }

    private FrameLayout.LayoutParams makeCircleLP(View view) {
        int[] offset = new int[2];
        view.getLocationInWindow(offset);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(size, size);

        flp.leftMargin = offset[0];
        flp.topMargin = offset[1];
        return flp;
    }

    private View createCircleView() {
        View circle = new View(this);
        circle.setBackgroundResource(R.drawable.ic_red_circle);
        return circle;
    }


}
