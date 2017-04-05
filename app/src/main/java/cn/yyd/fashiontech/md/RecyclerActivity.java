package cn.yyd.fashiontech.md;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.yyd.fashiontech.BaseActivity;
import cn.yyd.fashiontech.R;

public class RecyclerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        init();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.btn_change_appbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((AppBarLayout)toolbar.getParent()).
            }
        });
    }

    private RecyclerView mList;

    private void init() {
        mList = (RecyclerView) findViewById(R.id.content_recycler_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SimpleAdapter adapter = new SimpleAdapter(makeData());
        mList.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(mList);

    }

    private List<String> makeData() {
        final int maxSize = 1000;
        List<String> data = new ArrayList<>(maxSize);
        Random rd = new Random(SystemClock.currentThreadTimeMillis());
        data.add("AAAAAAAAAAA");
        data.add("BBBBBBBBBBB");
        for (int i = 0; i < maxSize; i++) {
            data.add(String.valueOf(rd.nextInt()));
        }
        return data;
    }

    static class SimpleAdapter extends RecyclerView.Adapter<SimpleHolder> {

        SimpleAdapter(List<String> data) {
            this.mData = data;
        }

        private List<String> mData;


        @Override
        public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleHolder(createItemView(parent));
        }

        private View createItemView(ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_recycler_item, parent, false);
        }

        @Override
        public void onBindViewHolder(SimpleHolder holder, int position) {
            holder.tvTittle.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        void moveItem(int srcPosition, int destPosition) {
            Collections.swap(mData, srcPosition, destPosition);
            notifyItemMoved(srcPosition, destPosition);
        }
    }

    static class SimpleHolder extends RecyclerView.ViewHolder {

        private TextView tvTittle;

        public SimpleHolder(View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }

    static class TouchHelperCallback extends ItemTouchHelper.Callback {

        SimpleAdapter mAdapter;

        TouchHelperCallback(SimpleAdapter adapter) {
            mAdapter = adapter;

        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            mAdapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public boolean isItemViewSwipeEnabled() {

            return true;
        }
    }

}
