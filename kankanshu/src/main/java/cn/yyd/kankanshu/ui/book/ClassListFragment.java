package cn.yyd.kankanshu.ui.book;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.utils.ViewUtils;
import cn.yyd.kankanshu.utils.logging.Ln;
import cn.yyd.kankanshu.view.AnchoredListView;
import cn.yyd.kankanshu.widget.Loading;

/**
 * Created by YanYadi on 2017/4/20.
 */
public class ClassListFragment extends BaseFragment implements CategoryFragment.OnCategoryCheckedListener {

    @BindView(R.id.lv_class_list_one) AnchoredListView mListLevelOne;

    @BindView(R.id.tv_category) TextView mCategory;

    @BindView(R.id.vs_category) ViewStub vs_category;

    @BindView(R.id.tv_publisher) TextView mTvPublisher;

    @BindView(R.id.category_subject) FlexboxLayout mSubjects;

    @BindView(R.id.tv_subject) TextView mTvSubject;

    private Unbinder unbinder;
    private int mSubjectHeight = -1;
    private View.OnClickListener onTagClicker = new View.OnClickListener() {
        @Override public void onClick(View v) {
            //TODO 保存 subject id
            ViewUtils.checkedItem((Checkable) v, v.getParent());
            if (((int) v.getTag()) == 0) {
                mTvSubject.setText(R.string.tip_choice_subject);
            } else {
                mTvSubject.setText(((TextView) v).getText());
            }
            hideSubjects();
            query();
        }
    };

    @OnClick(R.id.frame_btn_subjects) void showSubjects() {
        if (mSubjects.getChildCount() == 0) {
            fillItem(mSubjects, resources.getStringArray(R.array.subject), R.id.type_subject);
        }
        if (mSubjects.getVisibility() == View.VISIBLE) {
            hideSubjects();
        } else {
            animShowSubjects();
        }
    }

    private void query() {
        Loading.show(getActivity()).show();
    }

    private void animShowSubjects() {
        mSubjects.setVisibility(View.VISIBLE);
        if (mSubjectHeight == -1) {
            final ViewTreeObserver observer = mSubjects.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    Ln.d("onGlobalLayout..");
                    if (mSubjects.getHeight() != 0) {
                        mSubjectHeight = mSubjects.getHeight();
                        observer.removeOnGlobalLayoutListener(this);
                        doAnimShow();
                    }
                }
            });
            //如果没有得到 mSubjectHeight 则忽略这一帧的绘制。
            //实际上，在 onMeasure之后，layout 时总会调用 onGlobalLayout方法
            //在 layout 之后，才会绘制。
            //所以此举是多余的
//            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override public boolean onPreDraw() {
//                    Ln.d("onPreDraw..");
//                    if (mSubjectHeight == -1) return true;
//                    observer.removeOnPreDrawListener(this);
//                    return false;
//                }
//            });
        } else {
            doAnimShow();
        }
    }

    private void doAnimShow() {
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mSubjectHeight).setDuration(500);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override public void onAnimationUpdate(ValueAnimator animation) {
//                mSubjects.getLayoutParams().height = (int)animation.getAnimatedValue();
//                mSubjects.requestLayout();
//                mSubjects.invalidate();
//            }
//        });
//        valueAnimator.start();
        ValueAnimator animator = ObjectAnimator.ofFloat(mSubjects, "y", -mSubjectHeight, ((ViewGroup.MarginLayoutParams) mSubjects.getLayoutParams()).topMargin).setDuration(500);
        animator.start();
    }

    private void hideSubjects() {
        //指定两个值，第一个值作为初始值，第二个值作为结束值
//        ValueAnimator animator = ObjectAnimator.ofFloat(mSubjects, "y", ((ViewGroup.MarginLayoutParams) mSubjects.getLayoutParams()).topMargin, -mSubjectHeight).setDuration(500);

        //只指定一个值，该值将作为结束值，初始值会从 getXX 中获取。
        ValueAnimator animator = ObjectAnimator.ofFloat(mSubjects, "y", -mSubjectHeight).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                mSubjects.setVisibility(View.GONE);
            }
        });
        animator.start();
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
        mListLevelOne.setAdapter(new SimpleAdapter(getActivity(), R.layout.simple_list_item, R.id.tv_category, resources.getStringArray(R.array.classes)));
        mListLevelOne.setItemChecked(0, true);
        mListLevelOne.setAnchorView(getView().findViewById(R.id.tv_checked_thumb));
    }

    private void fillItem(FlexboxLayout container, String[] tags, @IdRes int type) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        for (int index = 0; index < tags.length; index++) {
            String tag = tags[index];
            CheckedTextView textview = (CheckedTextView) inflater.inflate(R.layout.simple_category_item, container, false);
            textview.setText(tag);
            //存储索引
            textview.setTag(index);
            //存储类型
            textview.setTag(R.id.type, type);
            textview.setOnClickListener(onTagClicker);
            container.addView(textview);
        }
        //默认选中第一个
        ((CheckedTextView) container.getChildAt(0)).setChecked(true);
    }

    @Override public void onChecked(SparseIntArray checkedItems) {
    }

    @Override public void onItemChecked(@IdRes int type, int typeIndex, CharSequence tag) {
        if (typeIndex != 0)
            mTvPublisher.setText(tag);
        else
            mTvPublisher.setText(R.string.tip_choice_publisher);
    }

    static class SimpleAdapter extends ArrayAdapter<String> implements AnchoredListView.AnchorAdapter {

        public SimpleAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override public String getAnchorText(int pos) {
            return getItem(pos);
        }
    }
}
