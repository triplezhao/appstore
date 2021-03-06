package com.potato.appstore.jiongtu.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemJiongtuListBinding;
import com.potato.appstore.jiongtu.data.bean.JiongtuAlbum;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.SPUtils;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;

/**
 * Created by ztw on 2015/9/21.
 */
public class JiongTuListAdapter extends BaseListAdapter {

    public JiongTuListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemJiongtuListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_jiongtu_list,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemJiongtuListBinding binding = (ItemJiongtuListBinding) ((VH)holder).getBinding();
        final JiongtuAlbum bean = (JiongtuAlbum)mData.get(position);
        binding.setBean(bean);
        Context context = binding.getRoot().getContext();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                PageCtrl.startJiongTuDetailActivity(context, bean);
            }
        });
        binding.tvItemAlbumTitle.setText(bean.getTitle());
        long aid = SPUtils.read(context,
                SPUtils.SP_NAME_DEFAULT,
                SPUtils.SP_KEY_JIONGTU + bean.getId(),
                -1L);
        if (aid != -1) {
            bean.setHaveRead(true);
            binding.tvItemAlbumTitle.setTextColor(context.getResources().getColor(
                    R.color.potato_gray3));
        } else {
            bean.setHaveRead(false);
            binding.tvItemAlbumTitle.setTextColor(context.getResources().getColor(
                    R.color.potato_black));
        }


        ImageLoaderUtil.displayImage(bean.getBigCover(), binding.ivItemAlbumPic, R.drawable.def_gray_big);
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
