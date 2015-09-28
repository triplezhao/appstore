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
import com.potato.appstore.jiongtu.data.bean.JiongtuPhoto;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;


/**
 * 相册适配器
 */
public class JiongTuAlbumGalleryAdapter extends BaseListAdapter {


	public JiongTuAlbumGalleryAdapter(Context context) {
		super(context);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemJiongtuGalleryBinding binding = DataBindingUtil.inflate(
				LayoutInflater.from(parent.getContext()),
				R.layout.item_jiongtu_gallery,
				parent,
				false);
		VH holder = new VH(binding.getRoot());
		holder.setBinding(binding);

		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final ItemJiongtuGalleryBinding binding = (ItemJiongtuGalleryBinding) ((VH)holder).getBinding();
		final JiongtuPhoto bean = (JiongtuPhoto) mData.get(position);
		binding.setBean(bean);
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
