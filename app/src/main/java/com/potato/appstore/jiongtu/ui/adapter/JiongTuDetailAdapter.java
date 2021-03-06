package com.potato.appstore.jiongtu.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ItemJiongtuDetailBinding;
import com.potato.appstore.jiongtu.data.bean.JiongtuPhoto;
import com.potato.appstore.jiongtu.ui.act.AlbumGalleryActivity;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.ShareUtil;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;

import java.util.List;

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
        Context context = binding.getRoot().getContext();
    // 优先显示content


        String content = bean.getContent();
        if (!TextUtils.isEmpty(content)) {
            binding.tvItemPhotoDesc.setMovementMethod(LinkMovementMethod
                    .getInstance());
            binding.tvItemPhotoDesc.setText(Html.fromHtml(content));
            binding.tvItemPhotoDesc.setVisibility(View.VISIBLE);
        }else{
            binding.tvItemPhotoDesc.setVisibility(View.GONE);
        }


        binding.tvItemPhotoIndex.setText(bean.getIndexOrder() + "");
        binding.tvItemPhotoTotal.setText(getCount() + "");
        // 图片
        String img = null;
        int type = bean.getType();
        if (type== 3) {//视频
            img = bean.getVideoCoverUrl();
        } else {
            img = bean.getBigUrl();
            if(type==2){//GIF
                binding.ivItemGifFlag.setVisibility(View.VISIBLE);
            }else{
                binding.ivItemGifFlag.setVisibility(View.GONE);
            }
        }
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                AlbumGalleryActivity.startPage(view.getContext(),
                        (List<JiongtuPhoto>)mData, mData.indexOf(bean));
            }
        });
        binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShareUtil.shareImage(view.getContext(), bean.getBigUrl());
                return false;
            }
        });
        ImageLoaderUtil.displayImage(bean.getBigUrl(), binding.ivItemPhoto, R.drawable.def_gray_big);

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
