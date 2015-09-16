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

package com.potato.demo.a.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.demo.databinding.FragmentAListBinding;
import com.potato.demo.R;
import com.potato.demo.a.data.bean.ABean;
import com.potato.demo.a.ui.viewbinder.AViewBinder;
import com.potato.chips.base.BaseFragment;
import com.potato.chips.base.BaseListAdapter;
import com.potato.library.view.refresh.RefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AListFragment extends BaseFragment {
    /** extrars */
    /** views */
    /** adapters */
    /** data */
    /**
     * logic
     */
    private List<ABean> mValues = new ArrayList<ABean>();
    private BaseListAdapter mAdapter;
    private FragmentAListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_a_list,
                container,
                false);


        binding.swipeContainer.setFooterView(getActivity(), binding.list, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(getActivity(), new AViewBinder());
        binding.list.setAdapter(mAdapter);

        binding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        binding.swipeContainer.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        binding.swipeContainer.setOnLoadListener(new RefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues.addAll(getRandomSublist(Cheeses.sCheeseStrings, 30));
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        binding.swipeContainer.setLoading(false);
                    }
                }, 3000);

            }
        });
        binding.swipeContainer.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                mAdapter.setDataList(mValues);
                mAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        }, 3000);
        return binding.getRoot();
    }

    private List<ABean> getRandomSublist(String[] array, int amount) {

        ArrayList<ABean> list = new ArrayList<ABean>(amount);
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            list.add(new ABean("title=" + i, Cheeses.getRandomIcon(), "content=" + array[random.nextInt(array.length)]));
        }
        return list;
    }


    @Override
    public void onClick(View view) {

    }


}
