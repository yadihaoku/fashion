package cn.yyd.kankanshu.ui.book;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;

/**
 * Created by YanYadi on 2017/4/20.
 */
public class BaseFragment extends Fragment {
    protected Resources resources;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        resources = getResources();
    }
}
