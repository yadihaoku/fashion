package cn.yyd.fashiontech;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.yyd.fashiontech.databinding.ActivityMainBinding;
import cn.yyd.fashiontech.drawable.LevelDrawableActivity;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityDataBinder binder = new MainActivityDataBinder();
        binding.setHandler(new MainActivityHandler());
        binding.setBinder(binder);

        Application application = getApplication();
        Context context = getApplicationContext();
        Context context1 = getBaseContext();

        if (application == context) {
            Log.i(TAG, "onCreate: true");
        }
        Log.i(TAG, "onCreate: " + application);
        Log.i(TAG, "onCreate: " + context);
        Log.i(TAG, "onCreate: " + context1);
        Log.i(TAG, "onCreate: " + getSystemService(ACTIVITY_SERVICE));

        final View ivAvatar = findViewById(R.id.iv_avatar);
        ivAvatar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i(TAG, "onGlobalLayout: size=" + (ivAvatar.getWidth() + "x" + ivAvatar.getHeight()));
            }
        });
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                ToastUtils.toast(MainActivity.this.getResources().getString(R.string.leak_canary_display_activity_label));
//                startActivity(new Intent(MainActivity.this, RxActivity.class));
//            }
//        }, 5000);

//        LeakCanary.refWatcher(this).build().watch();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: " + this);
    }
    public void toLevel(View view){
        startActivity(new Intent(this, LevelDrawableActivity.class));
    }
}
