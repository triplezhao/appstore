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

package com.potato.demo.jiongtu.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.demo.databinding.FragmentJiongtuListBinding;
import com.potato.demo.R;
import com.potato.demo.jiongtu.data.bean.JiongtuAlbum;
import com.potato.demo.jiongtu.data.parser.JiongtuAlbumListParser;
import com.potato.demo.jiongtu.data.request.JiongtuRequestBuilder;
import com.potato.demo.jiongtu.ui.viewbinder.JiongTuViewBinder;
import com.potato.chips.base.BaseFragment;
import com.potato.chips.base.BaseListAdapter;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.refresh.RefreshListView;

import java.util.ArrayList;

public class JiongTuListFragment extends BaseFragment {
    private static final String TAG = "ListFragmentJiongtu";
    /**
     * extrars
     */
    public static final String EXTRARS_SECTION_ID = "EXTRARS_SECTION_ID";
    public static final String EXTRARS_TITLE = "EXTRARS_TITLE";
    private long mSectionId;
    private String mTitle;
    private ArrayList<JiongtuAlbum> mList = new ArrayList<JiongtuAlbum>();
    private BaseListAdapter mAdapter;
    private JiongtuAlbumListParser mParser;
    private FragmentJiongtuListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSectionId = getArguments() == null ? 0 : getArguments().getLong(EXTRARS_SECTION_ID);
        mTitle = getArguments() == null ? "" : getArguments().getString(EXTRARS_TITLE);


        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_jiongtu_list,
                container,
                false);

        mParser = new JiongtuAlbumListParser();
        mBinding.swipeContainer.setFooterView(getActivity(), mBinding.list, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(getActivity(), new JiongTuViewBinder());
        mBinding.list.setAdapter(mAdapter);

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
        mBinding.swipeContainer.setOnLoadListener(new RefreshListView.OnLoadListener() {
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
        switch (view.getId()){
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

        Request request = JiongtuRequestBuilder.getAlbumListRequest(mSectionId, 0);
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

        Request request = JiongtuRequestBuilder.getAlbumListRequest(mSectionId, mParser.maxPublicDate);
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
        mList = mParser.parseToAlbumList(content);
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
        mBinding.swipeContainer.setRefreshing(false);
        if (mList != null && mList.size() != 0) {
            mBinding.swipeContainer.setLoadEnable(true);
        }

    }

    private void onLoadSucc(String content) {
        mBinding.swipeContainer.setLoading(false);
        ArrayList<JiongtuAlbum> moreData = mParser.parseToAlbumList(content);
        if (moreData == null || moreData.size() == 0) {
            mBinding.swipeContainer.setLoadEnable(false);
            return;
        }
        mList.addAll(moreData);
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
    }

}
