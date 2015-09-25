package com.potato.chips.base;

import android.view.ViewGroup;

import com.potato.library.adapter.BaseViewHolder;

/**
 * Created by ztw on 2015/7/22.
 */
public abstract class BaseViewBinder <VH extends BaseViewHolder>{

    public BaseViewBinder() {

    }

    //返回 inflate出 每个元素的 layout，可以根据type给不同的布局
    public abstract VH onCreateViewHolder(int position, int type, ViewGroup parent);

    //将数据设置到itemView上
    public abstract void onBindViewHolder(VH holder,Object bean, int position, int type);

}
