/*
 * 
 * File Name: ETKSimpleAdapter.java 
 * History:
 * Created by 闫亚迪 on 2015年7月29日
 */
package cn.yyd.fashiontech.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装 BaseAdapter ，利用泛型，减少不必要的类型转换操作。
 *
 * @param <T> 实际的实体类型
 * @author 闫亚迪 on 2015年7月29日
 */
public abstract class QuickAdapter<T> extends BaseAdapter implements AdapterHelper.ViewItemCreator {

    private final int[] mLayoutResId;
    protected LayoutInflater mInflater;
    protected Context mContext;
    private List<T> mData;

    public QuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public QuickAdapter(Context context, int layoutResId, List<T> list) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResId = new int[]{layoutResId};
        if (null != list)
            this.mData = list;
        else
            this.mData = new ArrayList<T>();
    }

    /**
     * @param context
     * @param list
     * @param layoutRes 0 个或 n 个 layoutResId ,顺序与 itemViewType 对应。<Br />
     *                  如果没有指定该参数，须在实例化QuickAdapter 时，<br />
     *                  重写 {@link #createItemView(int, ViewGroup, LayoutInflater)} 接口
     */
    public QuickAdapter(Context context, List<T> list, int... layoutRes) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResId = layoutRes;
        if (null != list)
            this.mData = list;
        else
            this.mData = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 添加一个 泛型对象，但不执行 notifyDataSetChange 方法。
     *
     * @param t
     */
    public void add(T t) {
        if (null == mData)
            return;
        mData.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (null == mData)
            return;
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        if (mLayoutResId.length == 1) return 0;
        return super.getItemViewType(position);
    }

    @Override public int getViewTypeCount() {
        if (mLayoutResId.length != 0) return mLayoutResId.length;
        return super.getViewTypeCount();
    }

    /**
     * 删除数据集中的某项
     *
     * @param position
     */
    public void removeItem(int position) {
        if (mData.size() > position) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void setData(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    /**
     * 清除之前数据
     */
    public void clear() {
        if (this.mData != null)
            this.mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AdapterHelper helper;
        if (mLayoutResId.length == 0) {
            helper = AdapterHelper.get(mContext, convertView, parent, position, this);
        } else {
            helper = AdapterHelper.get(mContext, convertView, parent, mLayoutResId[getItemViewType(position)], position);
        }
        T item = getItem(position);
        helper.setAssociatedObject(item);
        convert(helper, item);
        return helper.getView();
    }

    @Override public View createItemView(int position, ViewGroup parent, LayoutInflater inflater) {
        return new TextView(parent.getContext());
    }

    /**
     * 使用泛型，绑定 Adapter 中的实际对象
     *
     * @param helper
     * @param data
     */
    public abstract void convert(AdapterHelper helper, T data);
}
