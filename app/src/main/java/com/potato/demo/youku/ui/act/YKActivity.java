package com.potato.demo.youku.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.potato.chips.app.GlobleConstant;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.base.BaseListAdapter;
import com.potato.demo.R;
import com.potato.demo.databinding.ActivityYkVideosBinding;
import com.potato.demo.youku.data.bean.YKVideo;
import com.potato.demo.youku.data.parser.YKVideosByUserParser;
import com.potato.demo.youku.data.request.YKRequestBuilder;
import com.potato.demo.youku.ui.viewbinder.YKVideoViewBinder;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.refresh.RefreshListView;

import java.util.ArrayList;

/**
 * Created by ztw on 2015/7/3.
 */
public class YKActivity extends BaseActivity {

    public static final String TAG = "YKActivity";

    private ArrayList<YKVideo> mList = new ArrayList<YKVideo>();
    private BaseListAdapter mAdapter;
    private ActivityYkVideosBinding mBinding;

    private int mTotal = 0;
    private int mPage = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_yk_videos);

        mBinding.swipeContainer.setFooterView(mContext, mBinding.list, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(mContext, new YKVideoViewBinder());
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

    private void sendRequest2RefreshList() {

        Request request = YKRequestBuilder.videosByUser(GlobleConstant.youku_client_id, GlobleConstant.youku_def_uid, "", "", "1", "", "");

        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);
                mBinding.swipeContainer.showEmptyViewFail();

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
     * 刷新列表
     */
    private void sendRequest2LoadMoreList() {

        Request request = YKRequestBuilder.videosByUser(GlobleConstant.youku_client_id, GlobleConstant.youku_def_uid, "", "", mPage + 1 + "", "", "");

        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onLoadSucc(content);

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);
                mBinding.swipeContainer.showEmptyViewFail();
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
        YKVideosByUserParser parser = new YKVideosByUserParser(content);
        if (parser.isSucc()) {

            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            mList = parser.list;

            mAdapter.setDataList(mList);
            mAdapter.notifyDataSetChanged();
            if (mList != null && mList.size() != 0 && mList.size() < mTotal) {
                mBinding.swipeContainer.setLoadEnable(true);
            }
            mBinding.swipeContainer.showSucc();
        } else {
            mBinding.swipeContainer.showEmptyViewFail();
        }

    }

    private void onLoadSucc(String content) {
        YKVideosByUserParser parser = new YKVideosByUserParser(content);
        if (parser.isSucc()) {

            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            if (parser.list != null && parser.list.size() > 0) {
                mList.addAll(parser.list);
            }
            mAdapter.setDataList(mList);
            mAdapter.notifyDataSetChanged();
            if (mList == null || mList.size() == 0 || mList.size() >= mTotal) {
                mBinding.swipeContainer.setLoadEnable(false);
            } else {
                mBinding.swipeContainer.setLoadEnable(true);
            }
        } else {
            mBinding.swipeContainer.showEmptyViewFail();
        }
        mBinding.swipeContainer.setLoading(false);
    }
}
