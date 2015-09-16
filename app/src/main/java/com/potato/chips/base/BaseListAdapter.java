package com.potato.chips.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseListAdapter extends BaseAdapter {

    public Context mContext;

    public List mData = new ArrayList();
    ;
    public LayoutInflater mInflater;

    public BaseViewBinder mViewBinder;

    public BaseListAdapter(Context context,BaseViewBinder viewBinder) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mViewBinder = viewBinder;
    }

    //返回绑定的数据
    public void setDataList(List data) {
        if (mData == null) {
            mData = new ArrayList();
        }
        mData.clear();
        mData.addAll(data);
    }

   /* //返回 inflate出 每个元素的 layout，可以根据type给不同的布局
    protected abstract VH onCreateViewHolder(int position, int type);

    //将数据设置到itemView上
    protected abstract void onBindViewHolder(VH holder, final Object object, int position, int type);*/

    @Override
    public int getCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //item控件
        BaseViewHolder vh;
        if (convertView == null) {
            //findview  clumn
            vh = mViewBinder.onCreateViewHolder(position, getItemViewType(position), parent);
            convertView = vh.view;
        } else {
            vh = (BaseViewHolder) convertView.getTag();
        }
        mViewBinder.onBindViewHolder(vh, mData.get(position), position, getItemViewType(position));
        return convertView;
    }
}