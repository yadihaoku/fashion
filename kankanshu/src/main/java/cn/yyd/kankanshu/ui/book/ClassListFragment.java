package cn.yyd.kankanshu.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yyd.kankanshu.R;

/**
 * Created by YanYadi on 2017/4/20.
 */
public class ClassListFragment extends BaseFragment {

    @Inject public ClassListFragment(){

    }

    @BindView(R.id.lv_class_list_one) ListView mListLevelOne;
    private Unbinder unbinder;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_class_list, container, false);
        unbinder = ButterKnife.bind(this, itemView);
        return itemView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
