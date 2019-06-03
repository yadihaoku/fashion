package com.test.life;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.yyd.fashiontech.R;

public class LoginSuccessActivity extends Activity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        textView = (TextView) findViewById(R.id.tv_result);
    }
    public void showResult(View view){
        textView.setText(((TextView)view).getText());
    }
}
