package com.potato.appstore.store.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemAppListBinding;
import com.potato.appstore.store.data.bean.ApkInfo;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;

/**
 * Created by ztw on 2015/9/24.
 */
public class AppListAdapter extends BaseListAdapter {

    public AppListAdapter(Context context) {
        super(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int type) {

        ItemAppListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_app_list,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        ItemAppListBinding binding = (ItemAppListBinding) ((VH) holder).getBinding();
        final ApkInfo bean = (ApkInfo) mData.get(position);
        binding.setBean(bean);
        ImageLoaderUtil.displayImage(bean.getImage(), binding.ivPic, R.drawable.def_gray_small);


        binding.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(bean.getUrl());
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(downloadIntent);
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.toast(v.getContext(), "进入详情");
//                Intent intent = new Intent(v.getContext(), AppDetailActivity.class);
//                intent.putExtra("EXTRA_APPINFO", mAppInfos.get(position));
//                v.getContext().startActivity(intent);
            }
        });


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
