package com.potato.appstore.store.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemAppListBinding;
import com.potato.appstore.store.data.bean.AppInfo;
import com.potato.chips.base.BaseListAdapter;
import com.potato.chips.base.BaseViewHolder;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;

/**
 * Created by ztw on 2015/9/24.
 */
public class AppListAdapter  extends BaseListAdapter{

    public AppListAdapter(Context context) {
        super(context);
    }

    @Override
    public VH onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemAppListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                com.potato.appstore.R.layout.item_app_list,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object object, int position, int type) {
        ItemAppListBinding binding = (ItemAppListBinding) ((VH)holder).getBinding();
        final AppInfo bean = (AppInfo)object;
        binding.setBean(bean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                UIUtils.toast(context, "下载apk");
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
