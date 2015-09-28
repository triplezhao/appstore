package com.potato.appstore.jiongtu.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemJiongtuGalleryBinding;
import com.potato.appstore.databinding.ItemJiongtuGalleryGifBinding;
import com.potato.appstore.jiongtu.data.bean.JiongtuPhoto;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.library.util.L;


/**
 * 相册适配器
 */
public class JiongTuAlbumGalleryAdapter extends BaseListAdapter {


    public JiongTuAlbumGalleryAdapter(Context context) {
        super(context);
    }


    @Override
    public int getItemViewType(int position) {
        final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
        if (bean.getType() == 2) {//动态
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = null;
        if (viewType == 1) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_jiongtu_gallery,
                    parent,
                    false);
        } else {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_jiongtu_gallery_gif,
                    parent,
                    false);

        }
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 1) {
            ItemJiongtuGalleryBinding binding = (ItemJiongtuGalleryBinding) ((VH) holder).getBinding();
            final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
            binding.setBean(bean);
            ImageLoaderUtil.displayImage(bean.getBigUrl(), binding.ivPic, R.drawable.def_gray_big);
            L.i("onBindViewHolder","type=1"+"url="+bean.getBigUrl());
        } else {
            ItemJiongtuGalleryGifBinding binding = (ItemJiongtuGalleryGifBinding) ((VH) holder).getBinding();
            final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
            binding.setBean(bean);
//            binding.ivPic.setGifSDPath(ImageLoader.getInstance().getDiskCache().get(bean.getBigUrl()).getAbsolutePath());
            binding.ivPic.setGifUrl(bean.getBigUrl());
//            L.i("onBindViewHolder", "type=1" + "url=" + ImageLoader.getInstance().getDiskCache().get(bean.getBigUrl()).getAbsolutePath());
            ImageLoaderUtil.displayImage(bean.getBigUrl(), binding.ivPic, R.drawable.def_jiongtu);
        }

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
