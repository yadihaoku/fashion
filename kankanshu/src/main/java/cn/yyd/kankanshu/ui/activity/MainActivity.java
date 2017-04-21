package cn.yyd.kankanshu.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.ui.book.ClassListFragment;
import cn.yyd.kankanshu.utils.logging.Ln;
import cn.yyd.kankanshu.view.BubbleCheckedTextView;

/**
 * @author YadiYan
 * 看看书 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_discover) BubbleCheckedTextView mTvDiscover;

    @BindView(R.id.frame_main) FrameLayout mMainFrame;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.textView4) TextView mTextView;


    @Inject ClassListFragment mainFragment;
//    @Inject BookListInterface mBookList;

    /**
     * 底部BottomBar 单击事件
     *
     * @param tabItem
     */
    @OnClick({R.id.tv_discover, R.id.tv_settings, R.id.tv_main}) void onTabClick(BubbleCheckedTextView tabItem) {
        if (tabItem.isChecked()) return;
        ViewGroup viewGroup = (ViewGroup) tabItem.getParent();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            BubbleCheckedTextView item = (BubbleCheckedTextView) viewGroup.getChildAt(i);
            item.setChecked(item == tabItem);
        }
        Ln.d(getApplicationContext().getApplicationContext().getApplicationContext().getApplicationContext());
        Ln.d(getApplication().getApplicationContext().getApplicationContext());
        Ln.d(getApplicationContext().getApplicationContext());
        Ln.d(getBaseContext().getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kk_main);
        ButterKnife.bind(this);

        final BubbleCheckedTextView tvMain = (BubbleCheckedTextView) findViewById(R.id.tv_main);
        tvMain.setChecked(true);
        assert savedInstanceState != null;

    }

}
