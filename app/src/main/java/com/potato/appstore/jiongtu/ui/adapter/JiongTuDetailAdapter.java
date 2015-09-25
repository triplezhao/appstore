package com.potato.appstore.jiongtu.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemJiongtuDetailBinding;
import com.potato.appstore.jiongtu.data.bean.JiongtuPhoto;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.ShareUtil;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;

/**
 * Created by ztw on 2015/9/21.
 */
public class JiongTuDetailAdapter extends BaseListAdapter {

    public JiongTuDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int type) {

        ItemJiongtuDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_jiongtu_detail,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        final ItemJiongtuDetailBinding binding = (ItemJiongtuDetailBinding) ((VH)holder).getBinding();
        final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
        binding.setBean(bean);
//        L.i("Picasso", "URL," + bean.getBigUrl());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareImage(view.getContext(), bean.getBigUrl());
            }
        });
        ImageLoaderUtil.displayImage(bean.getBigUrl(), binding.ivPic, R.drawable.def_gray_big);

    }

    public static class VH extends BaseViewHolder {

        private ViewDataBinding binding;

        public VH(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
