package cn.yyd.kankanshu.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.ui.book.ClassListFragment;
import cn.yyd.kankanshu.utils.ViewUtils;
import cn.yyd.kankanshu.view.BubbleCheckedTextView;

/**
 * @author YadiYan
 *         看看书 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_discover) BubbleCheckedTextView mTvDiscover;

    @BindView(R.id.frame_main) FrameLayout mMainFrame;

    @BindView(R.id.toolbar) Toolbar mToolbar;


    /**
     * 底部BottomBar 单击事件
     *
     * @param tabItem
     */
    @OnClick({R.id.tv_discover, R.id.tv_settings, R.id.tv_main}) void onTabClick(BubbleCheckedTextView tabItem) {
        ViewUtils.checkedItem(tabItem, (ViewGroup) tabItem.getParent());
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kk_main);


        ButterKnife.bind(this);

        initContent();

        final BubbleCheckedTextView tvMain = (BubbleCheckedTextView) findViewById(R.id.tv_main);
        tvMain.setChecked(true);



    }

    private void initContent() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment content = fragmentManager.findFragmentById(R.id.frame_main);
        if (null == content)
            content = ClassListFragment.instantiate(this, ClassListFragment.class.getName());

        getFragmentManager().beginTransaction().replace(R.id.frame_main, content).commitAllowingStateLoss();
    }

}
