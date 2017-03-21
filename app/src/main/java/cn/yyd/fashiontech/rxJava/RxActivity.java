package cn.yyd.fashiontech.rxJava;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

import cn.yyd.fashiontech.R;
import cn.yyd.fashiontech.utils.ToastUtils;

public class RxActivity extends AppCompatActivity {

    private static final String TAG = "RxActivity";
    private PhantomCustomQueue<Object> strQueue = new PhantomCustomQueue<>();
    ;
    private List<Temp> list;
    private List<PhantomReference> referencesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list != null) {
                    list.clear();
                    list = null;
                    referencesList.clear();
                    referencesList = null;
                    System.runFinalization();
                    System.gc();
                }
                Intent toResult = new Intent(RxActivity.this, ResultActivity.class);
                startActivityForResult(toResult, 10);
            }
        });


        new Thread() {
            @Override
            public void run() {
                new MemoryTest().test();
                while (true) {
                    strQueue.watchAll();
                }
            }
        }.start();

        try

        {
            Thread.sleep(2000);
        } catch (
                InterruptedException e
                )

        {
            e.printStackTrace();
        }

        fillQueue();
    }

    private void fillQueue() {


        logMemInfo();
        if (list == null)
            list = new ArrayList<>();
        if (referencesList == null)
            referencesList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Temp tmp = new Temp();
            referencesList.add(new PhantomReference<>(tmp, strQueue));
            list.add(tmp);
        }

    }

    private void logMemInfo() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        Log.i(TAG, "fillQueue: AM_ availMem" + Formatter.formatFileSize(getBaseContext(), mi.availMem));
        Log.i(TAG, "fillQueue: AM_ totalMem" + Formatter.formatFileSize(getBaseContext(), mi.totalMem));
        Log.i(TAG, "fillQueue: AM_ 杀进程 门槛" + Formatter.formatFileSize(getBaseContext(), mi.threshold));

        Log.i(TAG, "fillQueue: maxMemory + " + (Formatter.formatFileSize(this, Runtime.getRuntime().maxMemory())));
        Log.i(TAG, "fillQueue: totalMemory + " + (Formatter.formatFileSize(this, Runtime.getRuntime().totalMemory())));
        Log.i(TAG, "fillQueue: freeMemory + " + (Formatter.formatFileSize(this, Runtime.getRuntime().freeMemory())));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: " + this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "onSaveInstanceState: " + this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK)
                Log.i(TAG, "onActivityResult: OK");
            else
                Log.i(TAG, "onActivityResult: " + (resultCode == RESULT_CANCELED));
        }

        ToastUtils.toast(String.format("reqCode = %d  resCode = %d", requestCode, resultCode));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: " + this);
    }

    static class PhantomCustomQueue<T> extends ReferenceQueue<T> {

        void watchAll() {
            Log.i(TAG, "watchAll: beginning====================");
            Reference<T> referent = null;
                try {

                referent = (Reference<T>) remove(0);
                Log.i(TAG, "watchAll: collect " + referent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i(TAG, "watchAll:  watch interrupt... ");
                }

//            Log.i(TAG, "watchAll: ended======================");
        }
    }

    static class Temp {
        private int[] letters = new int[256 * 1024];

        Temp() {
            for (int i = 0; i < letters.length; i++)
                letters[i] = Integer.MAX_VALUE;

        }
    }
}
