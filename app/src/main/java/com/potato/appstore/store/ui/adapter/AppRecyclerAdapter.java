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
import com.potato.appstore.databinding.ItemAppBinding;
import com.potato.appstore.store.data.bean.ApkInfo;
import com.potato.appstore.store.ui.fragment.AppListRecycleFragment;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;
import com.potato.library.adapter.BaseRecyclerViewAdapter;
import com.potato.library.adapter.BaseViewHolder;


/**
 * Created by ztw on 2015/9/24.
 */
public class AppRecyclerAdapter extends BaseRecyclerViewAdapter {

    AppListRecycleFragment mFm = null;

    public AppRecyclerAdapter(Context context) {
        super(context);
    }

    public void setFragment(AppListRecycleFragment fm){
        mFm = fm;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        ItemAppBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_app,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemAppBinding binding = (ItemAppBinding) ((VH) holder).getBinding();
        final ApkInfo bean = (ApkInfo) mData.get(position);
        binding.setBean(bean);
        ImageLoaderUtil.displayImage(bean.getImage(), binding.ivIcon, R.drawable.def_gray_small);

        binding.tvName.setText(bean.getName());
        binding.tvDownloadPerSize.setText(bean.getDownloadPerSize());
        binding.tvStatus.setText(bean.getStatusText());
        binding.progressBar.setProgress(bean.getProgress());
        binding.btnDownload.setText(bean.getButtonText());

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (mFm!=null) {
//                    mFm.download(position,bean);
//                }
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
