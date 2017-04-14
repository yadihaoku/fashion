package cn.yyd.fashiontech;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by YanYadi on 2017/4/5.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.i(TAG, "onCreate: " + this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart: " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: " + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: " + this);
    }
}
