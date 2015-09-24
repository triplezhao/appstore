package com.potato.chips.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context mContext;

    public List mData = new ArrayList();
    ;
    public LayoutInflater mInflater;


    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
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
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}