package cn.yyd.kankanshu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import cn.yyd.kankanshu.view.BubbleCheckedTextView;


/**
 * 看看书 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kk_main);

        final BubbleCheckedTextView  tvMain = (BubbleCheckedTextView) findViewById(R.id.tv_main);
        tvMain.setChecked(true);
        tvMain.setOnClickListener(this);
        findViewById(R.id.tv_discover).setOnClickListener(this);
        findViewById(R.id.tv_settings).setOnClickListener(this);

        tvMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMain.setBubbleText("1221");
            }
        },5_000);
    }


    @Override
    public void onClick(View v) {
        BubbleCheckedTextView tabItem = (BubbleCheckedTextView) v;
        if (tabItem.isChecked()) return;
        tabItem.setBubbleText(null);
        ViewGroup viewGroup = (ViewGroup) v.getParent();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            tabItem = (BubbleCheckedTextView) viewGroup.getChildAt(i);
            if (tabItem == v)
                tabItem.setChecked(true);
            else
                tabItem.setChecked(false);
        }

    }
}
