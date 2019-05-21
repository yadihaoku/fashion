package cn.yyd.kankanshu.ui.book;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yyd.kankanshu.R;
import cn.yyd.kankanshu.utils.ViewUtils;
import cn.yyd.kankanshu.utils.logging.Ln;

/**
 * Created by YanYadi on 2017/6/20.
 */
public class CategoryFragment extends BaseFragment {
    @BindView(R.id.category_publisher)
    FlexboxLayout mPublisher;

    private SparseIntArray mCheckedItems;

    private Unbinder unbinder;
    private OnCategoryCheckedListener onCheckedListener;
    private View.OnClickListener onTagClicker = new View.OnClickListener() {
        @Override public void onClick(View v) {
            if (mCheckedItems == null)
                mCheckedItems = new SparseIntArray();

            final int type = (int) v.getTag(R.id.type);
            final int typeIndex = (int) v.getTag();
            mCheckedItems.put(type, typeIndex);

            ViewUtils.checkedItem((Checkable) v, v.getParent());

            hideSelf();
            if (onCheckedListener != null) {
                onCheckedListener.onItemChecked(type, typeIndex, ((TextView) v).getText());
            }
        }
    };

    private void hideSelf() {
        getFragmentManager().beginTransaction()
                .hide(this)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }

    public void setOnCheckedListener(OnCategoryCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_class_category, container, false);

    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        fillItem(mPublisher, resources.getStringArray(R.array.publishers), R.id.type_publisher);
        final View contentView = getView();
        if (contentView != null) {
            contentView.setOnTouchListener(new View.OnTouchListener() {
                int mLastMotionY, mLastMotionX;
                int mTouchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
                boolean mIsBeingDragged;

                @Override public boolean onTouch(View v, MotionEvent ev) {
//                    MotionEvent vtev = MotionEvent.obtain(ev);
                    final int actionMasked = ev.getActionMasked();
                    switch (actionMasked) {
                        case MotionEvent.ACTION_MOVE:
                            if (mIsBeingDragged) break;
                            final int y = (int) ev.getY();
                            final int x = (int) ev.getX();
                            final int yDiff = Math.abs(y - mLastMotionY);
                            final int xDiff = Math.abs(x - mLastMotionX);
                            if (yDiff > mTouchSlop || xDiff > mTouchSlop) {
                                mIsBeingDragged = true;
                                Ln.d("being Dragged");
                            }
                            break;
                        case MotionEvent.ACTION_DOWN:
                            mLastMotionX = (int) ev.getX();
                            mLastMotionY = (int) ev.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!mIsBeingDragged) {
                                hideSelf();
                            }
                            mIsBeingDragged = false;
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mIsBeingDragged = false;
                            break;
                    }
                    return false;
                }
            });
        }
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

    @Override public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }

    interface OnCategoryCheckedListener {
        /**
         * 类型选择后，回调函数 <br />
         * 暂时不使用
         *
         * @param checkedItems 选中的类别. key 可能的值为
         *                     <br /> {@link cn.yyd.kankanshu.R.id#type_class} <br />
         *                     {@link cn.yyd.kankanshu.R.id#type_publisher} ,  {@link cn.yyd.kankanshu.R.id#type_publisher}
         */
        void onChecked(SparseIntArray checkedItems);

        void onItemChecked(@IdRes int type, int typeIndex, CharSequence tag);
    }
}
