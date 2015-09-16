/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.potato.library.view.slidingtab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyou.model.common.R;
import com.potato.library.util.L;


/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 */
public class SlidingTabLayout extends HorizontalScrollView {

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        int getDividerColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 5;
    private int textsize = 24;
    private int selectedTextsize = textsize;
    private int mTitleOffset;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;
    private  int  mSelectedColor= Color.BLACK;
    private  int  mUnSelectedColor= Color.GRAY;
    private TabCreater tabCreater = new DefaultTabCreater();

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSelectedColor=getResources().getColor(R.color.text_title_color_press);
        mSelectedColor=getResources().getColor(R.color.text_title_color_normal);
        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);
        
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     *
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
     * Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SlidingTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }


    /** 
    * @Title: setViewPager 
    * @Description: TODO(在设置好字体大小、颜色之后，最后再调用此方法) 
    * @param: @param viewPager   在设置好字体大小、颜色之后，最后再调用此方法
    * @return: void    返回类型
    * @date: 2014-4-16 下午8:35:20
    */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context,int position) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
        textView.setText(mViewPager.getAdapter().getPageTitle(position));
        int padding = (int) (1 * getResources().getDisplayMetrics().density);
        int bottom_padding = (int) (1 * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, bottom_padding, padding, bottom_padding);
        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            tabView = tabCreater.getTabView(i);
//            tabTitleView.setText(adapter.getPageTitle(i));
            //mahe add for custom tab view START 2014-8-25
//            if(!TextView.class.isInstance(tabView)){
//            	TextView tabTextView = (TextView) tabView.findViewById(R.id.tab_textivew);
//            	tabTextView.setText(mViewPager.getAdapter().getPageTitle(i));
//            }
            //mahe add for custom tab view END 2014-5-25
            tabView.setOnClickListener(tabClickListener);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.weight = 1;
            tabView.setLayoutParams(layoutParams);
            mTabStrip.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       L.i("onAttachedToWindow", "onAttachedToWindow");
        if (mViewPager != null) {
//            mTabStrip.onViewPagerPageChanged(2, 0f);
            this.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    L.i("onAttachedToWindow","post:"+mViewPager.getCurrentItem());
                    scrollToTab(mViewPager.getCurrentItem(), 0);
                    refreshState(mViewPager.getCurrentItem());
                }
            });

        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

                scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            //设置字体颜色
            refreshState(position);


            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i,true);
                    return ;
                }
            }
        }
    }
    
    /** 
    * @Title: refreshState 
    * @Description: TODO(刷新title字体颜色) 
    * @param: @param position    设定文件 
    * @return: void    返回类型
    * @date: 2014-4-11 下午2:32:07
    */
    private void refreshState(int position){
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
           View tabView = mTabStrip.getChildAt(i);
           tabCreater.setTabView(tabView, i, position == i);
        }
    }
    
    /** 
    * @Title: setSelectedColor 
    * @Description: TODO(设置选中字体颜色) 
    * @param: @param mSelectedColor    设定文件 
    * @return: void    返回类型
    * @date: 2014-4-11 下午2:31:51
    */
    public void setSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
    }

    /** 
    * @Title: setUnSelectedColor 
    * @Description: TODO(设置非选中字体颜色) 
    * @param: @param mUnSelectedColor    设定文件 
    * @return: void    返回类型
    * @date: 2014-4-11 下午2:31:18
    */
    public void setUnSelectedColor(int mUnSelectedColor) {
        this.mUnSelectedColor = mUnSelectedColor;
    }
    
    /** 
    * @Title: setSelectBitmap 
    * @Description: TODO(设置选中状态下面的图片) 
    * @param: @param mSelectBitmap 图片
    * @return: void    返回类型
    * @date: 2014-4-11 下午2:30:07
    */
    public void setSelectBitmap(Bitmap mSelectBitmap) {
        mTabStrip.setSelectBitmap(mSelectBitmap);
    }
    public void setSelectBitmap(int rId) {
        Bitmap mSelectBitmap = BitmapFactory.decodeResource(getResources(), rId);
        mTabStrip.setSelectBitmap(mSelectBitmap);
    }
    /** 
    * @Title: setType 
    * @Description: TODO( 0选中的下滑线为 原尺寸， 1 为拉伸到item尺寸) 
    * @param: @param type    0选中的下滑线为 原尺寸， 1 为拉伸到item尺寸
    * @return: void    返回类型
    * @date: 2014-4-16 下午8:09:26
    */
    public void setType(int type) {
        mTabStrip.setType(type);
    }
    public  void setTextSize(int textsize) {
        this.textsize = textsize;
    }
    
    
    public void setTabCreater(TabCreater tabCreater){
        this.tabCreater=tabCreater;
    }
    
    private class DefaultTabCreater implements TabCreater{

        @Override
        public View getTabView(int position) {
            // TODO Auto-generated method stub
            return createDefaultTabView(getContext(),position);
        }

        @Override
        public void setTabView(View view,int position, boolean isSelect) {
            // TODO Auto-generated method stub
            TextView tvitem=(TextView)view;
            if (isSelect) {
                tvitem.setTextColor(mSelectedColor);
                tvitem.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedTextsize);
            }else{
                tvitem.setTextColor(mUnSelectedColor);
                tvitem.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
            }
        }
        
    }
    
    public interface TabCreater {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        View getTabView(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        void setTabView(View view, int position, boolean isSelect);
        

    }
    
    //mahe add for show chatroom START 2014-8-8
    public View getTabLayoutByIndex(int position){
    	return mTabStrip.getChildAt(position);
    }
    //mahe add for show chatroom END 2014-8-8

	public int getSelectedTextsize() {
		return selectedTextsize;
	}

	public void setSelectedTextsize(int selectedTextsize) {
		this.selectedTextsize = selectedTextsize;
	}
}
