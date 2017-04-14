package cn.yyd.kankanshu.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by YanYadi on 2017/4/13.
 */

public class BaseActivity extends FragmentActivity {
    @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
    }
}
