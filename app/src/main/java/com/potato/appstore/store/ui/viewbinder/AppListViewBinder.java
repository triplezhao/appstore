package com.potato.appstore.store.ui.viewbinder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemAppListBinding;
import com.potato.appstore.store.data.bean.AppInfo;
import com.potato.chips.base.BaseViewBinder;
import com.potato.chips.base.BaseViewHolder;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;

/**
 * Created by ztw on 2015/7/22.
 */
public class AppListViewBinder extends BaseViewBinder<AppListViewBinder.ViewHolder> {

    public AppListViewBinder() {
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemAppListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                com.potato.appstore.R.layout.item_app_list,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Object object, int position, int type) {
        ItemAppListBinding binding = (ItemAppListBinding) holder.getBinding();
        final AppInfo bean = (AppInfo)object;
        binding.setBean(bean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                UIUtils.toast(context,"下载apk");
//                PageCtrl.startJiongTuDetailActivity(context, bean);
            }
        });
//        Picasso.with(binding.getRoot().getContext())
//                .load(bean.getBigCover())
//                .placeholder(R.drawable.def_gray_big)
////                .resize(100,100)
//                .into(binding.ivPic);
        ImageLoaderUtil.displayImage(bean.getBigCover(), binding.ivPic, R.drawable.def_gray_small);
    }

    public static class ViewHolder extends BaseViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
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
