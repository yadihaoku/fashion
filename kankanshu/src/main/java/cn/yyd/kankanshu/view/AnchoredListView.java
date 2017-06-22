package cn.yyd.kankanshu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by YanYadi on 2017/6/21.
 */
public class AnchoredListView extends ListView {
    private CheckedTextView mAnchorView;
    private OnScrollListener mScrollListener = new OnScrollListener() {
        @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            checkCheckedStatusChange(firstVisibleItem, visibleItemCount, totalItemCount);
        }
    };

    public AnchoredListView(Context context) {
        this(context, null);
    }

    public AnchoredListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnchoredListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AnchoredListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnScrollListener(mScrollListener);
    }

    private void checkCheckedStatusChange(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mAnchorView == null) return;
        final int checkedPos = getCheckedItemPosition();
        if (checkedPos != INVALID_POSITION) {

            if (checkedPos == firstVisibleItem || checkedPos < firstVisibleItem || checkedPos > firstVisibleItem + visibleItemCount) {
                mAnchorView.setVisibility(View.VISIBLE);
                mAnchorView.setText(((AnchorAdapter) getAdapter()).getAnchorText(checkedPos));
                return;
            }
        }
        mAnchorView.setVisibility(View.GONE);
    }

    @Override public boolean performItemClick(View view, int position, long id) {
        boolean isHandled = super.performItemClick(view, position, id);
        int totalItemCount = 0;
        if (getAdapter() != null) {
            totalItemCount = getAdapter().getCount();
        }
        checkCheckedStatusChange(getFirstVisiblePosition(), getChildCount(), totalItemCount);
        return isHandled;
    }

    public void setAnchorView(View view) {
        mAnchorView = (CheckedTextView) view;
        mAnchorView.setChecked(true);
        mAnchorView.setFocusable(false);
    }

    public void setAdapter(ListAdapter adapter) {
        if (!(adapter instanceof AnchorAdapter))
            throw new RuntimeException("adapter must a AnchorAdapter");
        super.setAdapter(adapter);
    }


    public interface AnchorAdapter extends ListAdapter {
        String getAnchorText(int pos);
    }


}
