package cn.yyd.fashiontech.gesture;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by YanYadi on 2017/2/14.
 */
public class GestureView extends View{

    private static final String TAG = "GestureView";
    private GestureDetectorCompat mGestureCompat;


    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureCompat = new GestureDetectorCompat(context, new CustomGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      boolean isConsumed =  mGestureCompat.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent: isConsumed " + isConsumed);
        return true;
//        return super.onTouchEvent(event);
    }


    class CustomGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: onDown");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress: onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp: onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll: onScroll");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress: onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling: onFling");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: onDoubleTap");
            return false;
        }
    }
}
