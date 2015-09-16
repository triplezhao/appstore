package com.potato.demo.jiongtu.ui.viewbinder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.demo.R;
import com.potato.demo.databinding.ItemJiongtuDetailBinding;
import com.potato.demo.jiongtu.data.bean.JiongtuPhoto;
import com.potato.chips.base.BaseViewBinder;
import com.potato.chips.base.BaseViewHolder;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.ShareUtil;

/**
 * Created by ztw on 2015/7/22.
 */
public class JiongTuDetailViewBinder extends BaseViewBinder<JiongTuDetailViewBinder.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemJiongtuDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_jiongtu_detail,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Object object, int position, int type) {
        final ItemJiongtuDetailBinding binding = (ItemJiongtuDetailBinding) holder.getBinding();
        final JiongtuPhoto bean = (JiongtuPhoto) object;
        binding.setBean(bean);
//        L.i("Picasso", "URL," + bean.getBigUrl());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.shareImage(view.getContext(), bean.getBigUrl());
            }
        });
//        Picasso.with(binding.getRoot().getContext())
//                .load(bean.getBigUrl())
//                .placeholder(R.drawable.def_gray_big1)
//                .resize(MainApplication.screenWidth, 0)
//                .into(binding.ivPic);
//        ViewUtil.changeViewLayerType(binding.ivPic, View.LAYER_TYPE_NONE);
//        binding.ivPic.setLayerType(View.LAYER_TYPE_NONE, null);
//        L.i("JiongTuDetailViewBinder", "hardware="+binding.ivPic.isHardwareAccelerated());
//        ;
//        String url = "http://mmbiz.qpic.cn/mmbiz/2pNzmMNj5xzdoaw4ic9SicDjBq9409XEwbaPT3rwyiaMQRkF84oTsw6q820ibWeMfk7icbBicZt2GZ0skib3VPDsJhZmw/640?tp=webp&wxfrom=5&wx_lazy=1";
        ImageLoaderUtil.displayImage(bean.getBigUrl(), binding.ivPic, R.drawable.def_gray_big);


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
