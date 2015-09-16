package com.potato.chips.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMutiRowAdapter extends BaseAdapter {

    public Context mContext;

    public List mData = new ArrayList();
    ;
    public LayoutInflater mInflater;

    public BaseViewBinder mViewBinder;

    public BaseMutiRowAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    //返回绑定的数据
    public void setDataList(List data) {
        if (mData == null) {
            mData = new ArrayList();
        }
        mData.clear();
        mData.add(data);
    }

    //返回每行有多少列
    public abstract int getRowNum();

  /*  //返回 inflate出 每个元素的 layout
    public abstract VH onCreateViewHolder();

    //将数据设置到itemView上
    public abstract void onBindViewHolder(VH holder, final Object object);*/


    @Override
    public int getCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        return (mData.size() - 1) / getRowNum() + 1;
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
        LinearLayout viewGroup = (LinearLayout) convertView;
        if (convertView == null) {
            viewGroup = new LinearLayout(mContext);
            viewGroup.setClickable(true);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.weight = 1;
            viewGroup.setOrientation(LinearLayout.HORIZONTAL);
            //findview  clumn
            for (int i = 0; i < getRowNum(); i++) {
                BaseViewHolder vh = mViewBinder.onCreateViewHolder(position, getItemViewType(position), parent);
                viewGroup.addView(vh.view, layoutParams);
            }
        }

        //绑定数据
        for (int i = 0; i < getRowNum(); i++) {
            Object data = null;
            try {
                data = mData.get(position * getRowNum() + i);
            } catch (Exception e) {
            }
            if (data != null) {
                mViewBinder.onBindViewHolder((BaseViewHolder) viewGroup.getChildAt(i).getTag(), data, position, getItemViewType(position));
                viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                viewGroup.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
        return viewGroup;
    }
}