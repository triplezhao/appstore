package com.potato.demo.a.ui.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.demo.databinding.ItemABinding;
import com.potato.demo.R;
import com.potato.demo.a.data.bean.ABean;
import com.potato.demo.a.ui.act.ADetailActivity;
import com.potato.chips.base.BaseViewBinder;
import com.potato.chips.base.BaseViewHolder;
import com.potato.library.util.L;
import com.squareup.picasso.Picasso;

/**
 * Created by ztw on 2015/7/22.
 */
public class AViewBinder extends BaseViewBinder<AViewBinder.AViewHolder> {
    @Override
    public AViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemABinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_a,
                parent,
                false);
        AViewHolder holder = new AViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(AViewHolder holder, Object object, int position, int type) {
        final ItemABinding binding = (ItemABinding) holder.getBinding();
        final ABean bean = (ABean) object;
        binding.setBean(bean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ADetailActivity.class);
                intent.putExtra(ADetailActivity.EXTRA_NAME, bean.getTitle());
                context.startActivity(intent);
            }
        });
        L.i("with", bean.getIcon());
//        Glide.with(binding.getRoot().getContext())
//                .load(bean.getIcon())
//                .fitCenter()
//                .placeholder(R.drawable.ic_launcher)
////                .into(binding.avatar);
//                .into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        binding.avatar.setImageDrawable(resource);
//                    }
//                });
        Picasso.with(binding.getRoot().getContext())
                .load(bean.getIcon())
                .placeholder(R.drawable.icon_tab_home)
                .resize(100,100)
                .into(binding.avatar);
    }

    public static class AViewHolder extends BaseViewHolder {

        private ViewDataBinding binding;

        public AViewHolder(View itemView) {
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
