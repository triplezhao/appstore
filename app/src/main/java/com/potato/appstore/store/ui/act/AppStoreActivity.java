package com.potato.appstore.store.ui.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.potato.appstore.R;
import com.potato.appstore.store.data.bean.AppSection;
import com.potato.appstore.store.data.parser.AppSectionListParser;
import com.potato.appstore.store.data.request.AppStoreRequestBuilder;
import com.potato.appstore.store.ui.fragment.AppListFragment;
import com.potato.appstore.store.ui.fragment.AppListRecycleFragment;
import com.potato.chips.base.BaseActivity;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2015/7/3.
 */
public class AppStoreActivity extends BaseActivity {

    public static final String TAG = "ActivityJiongtu";
    /** extrars */
    /**
     * views
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;
    /**
     * adapters
     */
    private HeaderPageAdapter adapter;
    /**
     * data
     */
    private List<AppSection> mList = new ArrayList<AppSection>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiongtu);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        bindData();
    }

    public void bindData() {
        adapter = new HeaderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Request request = AppStoreRequestBuilder.getSectionListRequest();
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {
            @Override
            public void onCacheLoaded(String content) {
                updateUI(content);
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                updateUI(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.i(TAG,errMsg+"");
            }
        }, RequestManager.CACHE_TYPE_NORMAL);

    }


    private void updateUI(String content) {
        L.i(TAG,content+"");
        if (TextUtils.isEmpty(content)) {
            return;
        }
       List<AppSection> list = AppSectionListParser.parseToSectionList(content);
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
            adapter.notifyDataSetChanged();
            tabLayout.setTabsFromPagerAdapter(adapter);
        }
    }

    private class HeaderPageAdapter extends FragmentStatePagerAdapter {

        public HeaderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AppSection obj = mList.get(position);
            L.d("In ViewPager#getItem, header: " + obj.getTitle() + ", position: "
                    + position);
            Bundle args = new Bundle();
            args.putLong(AppListRecycleFragment.EXTRARS_SECTION_ID, obj.getSectionId());
            args.putString(AppListRecycleFragment.EXTRARS_TITLE, obj.getTitle());
            AppListFragment pageFragement = (AppListFragment) Fragment.instantiate(mContext, AppListFragment.class.getName(), args);
            return pageFragement;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getTitle();
        }
    }
}
