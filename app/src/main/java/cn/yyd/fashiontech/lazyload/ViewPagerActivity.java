package cn.yyd.fashiontech.lazyload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.yyd.fashiontech.R;

public class ViewPagerActivity extends FragmentActivity {

    @BindView(R.id.vp_one)ViewPager viewPager;
    String[] colors = new String[]{"红色", "黑色", "白色", "绿色", "黄色", "粉色", "蓝色"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);


        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override public Fragment getItem(int position) {
                Bundle args = new Bundle();
                args.putString("color", colors[position]);

                return Fragment.instantiate(ViewPagerActivity.this, ColorFragment.class.getName(),args);
            }

            @Override public int getCount() {
                return colors.length;
            }
        });

    }
    public static class ColorFragment extends Fragment{
        private boolean mViewCreated;
        private boolean mMenuVisible;
        private String mColor;
        TextView tvColor;
        @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_color, container,false);
            tvColor = (TextView)view.findViewById(R.id.tv_color);

            return view;
        }

        @Override public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(savedInstanceState!=null)
                mColor = savedInstanceState.getString("color");
        }

        @Override public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("color", mColor);
        }

        @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            mViewCreated = true;
            preformFetchData();
        }
        private void preformFetchData(){
            if(!mViewCreated)return;
            if(!mMenuVisible)return;

            loadData();
        }

        private void loadData(){
            if(mColor == null) {
                mColor = getArguments().getString("color");
                System.out.println("===================> 请求数据  " + mColor);
            }else{
                System.out.println("===================> 读取缓存  " + mColor);
            }
            tvColor.setText(mColor);
            System.out.println("----> loadData " + mColor);
        }

        @Override public void setMenuVisibility(boolean menuVisible) {
            super.setMenuVisibility(menuVisible);
            mMenuVisible = menuVisible;
            preformFetchData();
        }
    }
}
