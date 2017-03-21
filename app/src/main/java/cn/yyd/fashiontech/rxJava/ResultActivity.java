package cn.yyd.fashiontech.rxJava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.yyd.fashiontech.MainActivity;
import cn.yyd.fashiontech.R;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        findViewById(R.id.btn_to_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(ResultActivity.this, MainActivity.class);
                toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toMain.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(toMain);
            }
        });
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
}
