package cn.yyd.kankanshu.ui.book;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.utils.logging.Ln;

/**
 * Created by YanYadi on 2017/4/20.
 */
public class ClassListFragment extends BaseFragment implements CategoryFragment.OnCategoryCheckedListener {

    @BindView(R.id.lv_class_list_one) ListView mListLevelOne;

    @BindView(R.id.tv_category) TextView mCategory;

    @BindView(R.id.vs_category) ViewStub vs_category;

    @BindView(R.id.tv_publisher) TextView mTvPublisher;
    private Unbinder unbinder;

    public ClassListFragment() {

    }

    @OnClick(R.id.tv_category) void choiceCategory(View view) {
        if (vs_category == null) {
            Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_category);
            if (fragment != null) {
                if (fragment.isHidden()) {
                    getChildFragmentManager().beginTransaction().show(fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
                } else {
                    getChildFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
                }
            } else {
                Ln.d("categoryList is null ");
            }
        } else {
            vs_category.inflate();
            vs_category = null;
            CategoryFragment fragment = (CategoryFragment) getChildFragmentManager().findFragmentById(R.id.fragment_category);
            fragment.setOnCheckedListener(this);
        }
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_list, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    private void init() {
        mListLevelOne.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListLevelOne.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, R.id.tv_category, resources.getStringArray(R.array.classes)));
        mListLevelOne.setItemChecked(0,true);
    }

    @Override public void onChecked(SparseIntArray checkedItems) {
    }

    @Override public void onItemChecked(@IdRes int type, int typeIndex, CharSequence tag) {
        if (typeIndex != 0)
            mTvPublisher.setText(tag);
        else
            mTvPublisher.setText(R.string.tip_choice_publisher);
    }
}
