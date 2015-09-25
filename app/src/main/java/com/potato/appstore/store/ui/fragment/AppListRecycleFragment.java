/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.potato.appstore.store.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.multithreaddownload.CallBack;
import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.core.DownloadException;
import com.potato.appstore.R;
import com.potato.appstore.databinding.FragmentAppRecyclerBinding;
import com.potato.appstore.databinding.ItemAppBinding;
import com.potato.appstore.store.data.bean.ApkInfo;
import com.potato.appstore.store.data.parser.ApkInfoListParser;
import com.potato.appstore.store.data.request.AppStoreRequestBuilder;
import com.potato.appstore.store.data.request.DataSource;
import com.potato.appstore.store.ui.adapter.AppRecyclerAdapter;
import com.potato.chips.base.BaseFragment;
import com.potato.chips.util.Utils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.refresh.RecyclerSwipeLayout;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AppListRecycleFragment extends BaseFragment {
    private static final String TAG = "ListFragmentJiongtu";
    /**
     * extrars
     */
    public static final String EXTRARS_SECTION_ID = "EXTRARS_SECTION_ID";
    public static final String EXTRARS_TITLE = "EXTRARS_TITLE";
    private long mSectionId;
    private String mTitle;
    private List mList = new ArrayList<ApkInfo>();
    private AppRecyclerAdapter mAdapter;
    private ApkInfoListParser mParser;
    private FragmentAppRecyclerBinding mBinding;


    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * Dir: /Download
     */
    private final File dir = new File(Environment.getExternalStorageDirectory(), "Download");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSectionId = getArguments() == null ? 0 : getArguments().getLong(EXTRARS_SECTION_ID);
        mTitle = getArguments() == null ? "" : getArguments().getString(EXTRARS_TITLE);


        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_app_recycler,
                container,
                false);

        mParser = new ApkInfoListParser("");


        mAdapter = new AppRecyclerAdapter(mContext);
        mAdapter.setFragment(this);

        mBinding.list.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.list.setAdapter(mAdapter);


        mBinding.swipeContainer.setFooterView(mContext, mBinding.list, R.layout.listview_footer);

        mBinding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });
        mBinding.swipeContainer.setOnLoadListener(new RecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });

        mBinding.swipeContainer.setEmptyView(mBinding.emptyView);
        mBinding.emptyView.setOnClickListener(this);
        mBinding.swipeContainer.showProgress();
        sendRequest2RefreshList();

        return mBinding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_view:
                mBinding.swipeContainer.showProgress();
                sendRequest2RefreshList();
                break;
        }
    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2RefreshList() {
        L.e(TAG, "请求图册列表:sectionId=" + mSectionId);

        Request request = AppStoreRequestBuilder.getAlbumListRequest(mSectionId, 0);
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取图册数据失败!!!,EMPTY_TYPE_ERROR" + this);
                mBinding.swipeContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeContainer.showEmptyViewFail();
                    }
                }, 2000);

            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                onRefreshSucc(content);
            }
        };
        RequestManager.requestData(
                request,
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2LoadMoreList() {
        L.e(TAG, "请求图册列表:sectionId=" + mSectionId);

        Request request = AppStoreRequestBuilder.getAlbumListRequest(mSectionId, 1000);
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onLoadSucc(content);

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取图册数据失败!!!,EMPTY_TYPE_ERROR" + this);
                mBinding.swipeContainer.setLoading(false);
            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
            }
        };
        RequestManager.requestData(
                request,
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    private void onRefreshSucc(String content) {
        mBinding.swipeContainer.showSucc();
//        mList = mParser.parseToAlbumList(content);
        mList = DataSource.getInstance().getData();
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
        mBinding.swipeContainer.setRefreshing(false);
        if (mList != null && mList.size() != 0) {
            mBinding.swipeContainer.setLoadEnable(true);
        }

    }

    private void onLoadSucc(String content) {
        mBinding.swipeContainer.setLoading(false);
//        ArrayList<ApkInfo> moreData = mParser.parseToAlbumList(content);
        List moreData = DataSource.getInstance().getData();
        if (moreData == null || moreData.size() == 0) {
            mBinding.swipeContainer.setLoadEnable(false);
            return;
        }
        mList.addAll(moreData);
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
    }


    private AppRecyclerAdapter.VH getViewHolder(int position) {

        return (AppRecyclerAdapter.VH) mBinding.list.findViewHolderForLayoutPosition(position);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mBinding.list.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    private String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }


    public void download(final int position, final ApkInfo apkInfo) {
        final ApkInfo bean = apkInfo;
        if (bean.getStatus() == bean.STATUS_DOWNLOADING || bean.getStatus() == bean.STATUS_CONNECTING) {
            DownloadManager.getInstance().pause(bean.getUrl());
        } else if (bean.getStatus() == bean.STATUS_COMPLETE) {
            Utils.installApp(mContext, new File(dir, bean.getName() + ".apk"));
        } else if (bean.getStatus() == bean.STATUS_INSTALLED) {
            Utils.unInstallApp(mContext, bean.getPackageName());
        } else {
            DownloadManager.getInstance().getDownloadProgress("");
            DownloadManager.getInstance().download(bean.getName() + ".apk", bean.getUrl(), dir, new CallBack() {

                @Override
                public void onDownloadStart() {
                    bean.setStatus(ApkInfo.STATUS_CONNECTING);
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onConnected(long total, boolean isRangeSupport) {
                    bean.setStatus(ApkInfo.STATUS_DOWNLOADING);
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onProgress(long finished, long total, int progress) {
                    String downloadPerSize = getDownloadPerSize(finished, total);
                    bean.setProgress(progress);
                    bean.setDownloadPerSize(downloadPerSize);
                    bean.setStatus(ApkInfo.STATUS_DOWNLOADING);
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvDownloadPerSize.setText(downloadPerSize);
                        binding.progressBar.setProgress(progress);
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onComplete() {
                    bean.setStatus(ApkInfo.STATUS_COMPLETE);
                    File apk = new File(dir, bean.getName() + ".apk");
                    if (apk.isFile() && apk.exists()) {
                        String packageName = Utils.getApkFilePackage(getActivity(), apk);
                        bean.setPackageName(packageName);
                        if (Utils.isAppInstalled(getActivity(), packageName)) {
                            bean.setStatus(ApkInfo.STATUS_INSTALLED);
                        }
                    }

                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onDownloadPause() {
                    bean.setStatus(ApkInfo.STATUS_PAUSE);
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onDownloadCancel() {
                    bean.setStatus(ApkInfo.STATUS_NOT_DOWNLOAD);
                    bean.setDownloadPerSize("");
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.tvDownloadPerSize.setText("");
                        binding.btnDownload.setText(bean.getButtonText());
                    }
                }

                @Override
                public void onFailure(DownloadException e) {
                    bean.setStatus(ApkInfo.STATUS_DOWNLOAD_ERROR);
                    bean.setDownloadPerSize("");
                    if (isCurrentListViewItemVisible(position)) {
                        AppRecyclerAdapter.VH holder = getViewHolder(position);
                        ItemAppBinding binding = (ItemAppBinding) (holder).getBinding();
                        binding.tvStatus.setText(bean.getStatusText());
                        binding.tvDownloadPerSize.setText("");
                        binding.btnDownload.setText(bean.getButtonText());


                    }
                    e.printStackTrace();
                }
            });
        }
    }
}

