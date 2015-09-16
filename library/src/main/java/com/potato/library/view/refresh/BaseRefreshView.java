package com.potato.library.view.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.potato.library.view.NormalEmptyView;

public class BaseRefreshView extends SwipeRefreshLayout {

    private int mEnd;

    public BaseRefreshView(Context context) {
        this(context, null);
        init(context);
    }

    public BaseRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mEnd = (int) (24 * metrics.density);
    }

    //设置刷新动画最大位置
    public void setEnd(int mEnd) {
        this.mEnd = mEnd;
    }

    public void setRefreshing(boolean isLoading) {
        setProgressViewOffset(false, 0, mEnd);
        super.setRefreshing(isLoading);
    }


    private NormalEmptyView mEmptyview;



    public void setEmptyView(NormalEmptyView emptyView){
        mEmptyview = emptyView;
    }

    public void showProgress(){
        if(mEmptyview!=null){
            mEmptyview.setEmptyType(NormalEmptyView.EMPTY_TYPE_GONE);
        }
        setRefreshing(true);
    }
    public void showSucc(){
        if(mEmptyview!=null){
            mEmptyview.setEmptyType(NormalEmptyView.EMPTY_TYPE_GONE);
        }
        setRefreshing(false);
    }
    public void showEmptyViewFail(){
        if(mEmptyview!=null) {
            mEmptyview.setEmptyType(NormalEmptyView.EMPTY_TYPE_ERROR);
        }
        setRefreshing(false);
    }
    public void showEmptyViewNoContent(){
        if(mEmptyview!=null) {
            mEmptyview.setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);
            setRefreshing(false);
        }
    }



}