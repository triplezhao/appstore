package com.potato.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyou.model.common.R;
import com.potato.library.util.L;


public class NormalEmptyView extends RelativeLayout {
    public static final int EMPTY_TYPE_LOADING = 1;
    public static final int EMPTY_TYPE_ERROR = 2;
    public static final int EMPTY_TYPE_NOCONTENT = 3;
    public static final int EMPTY_TYPE_GONE = 4;
    public TextView tv_empty_text;
    private View pb_empty_loading;
    private View pb_empty_fail;
    private ImageView iv_empty_nocontent;

    private int mEmptyRes = R.string.potato_content_empty;
    private int mLoadingRes = R.string.potato_loading;
    private int mErrorRes = R.string.potato_network_error;
    private int mEmptyDrawableRes = R.drawable.empty_nocontent;
    private int mErrorDrawableRes = R.drawable.empty_nocontent;
    private int mEmptyType = EMPTY_TYPE_GONE;

    private Context mContext;

    public NormalEmptyView(Context context) {
        super(context);
    }

    public NormalEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.normal_empty, this);
        tv_empty_text = (TextView) findViewById(R.id.tv_empty_text);
        pb_empty_loading = (View) findViewById(R.id.pb_empty_loading);
        pb_empty_fail = (View) findViewById(R.id.pb_empty_fail);
        iv_empty_nocontent = (ImageView) findViewById(R.id.iv_empty_nocontent);

        setBackgroundResource(R.color.potato_trans);

        setEmptyRes(R.string.potato_content_empty);

        setEmptyType(mEmptyType);

        L.i("NormalEmptyView", "NormalEmptyView()");
    }

    public void setEmptyType(int type) {

        this.setVisibility(View.VISIBLE);

        switch (type) {
            case EMPTY_TYPE_ERROR:
                tv_empty_text.setText(getErrorRes());
                pb_empty_fail.setVisibility(View.VISIBLE);
                pb_empty_loading.setVisibility(View.GONE);
                iv_empty_nocontent.setVisibility(View.GONE);
                setClickable(true);
                break;
            case EMPTY_TYPE_LOADING:
                tv_empty_text.setText(getLoadingRes());
                pb_empty_loading.setVisibility(View.VISIBLE);
                pb_empty_fail.setVisibility(View.GONE);
                iv_empty_nocontent.setVisibility(View.GONE);
                setClickable(false);
                break;
            case EMPTY_TYPE_NOCONTENT:
                tv_empty_text.setText(getEmptyRes());
                iv_empty_nocontent.setVisibility(View.VISIBLE);
                pb_empty_fail.setVisibility(View.GONE);
                pb_empty_loading.setVisibility(View.GONE);
                setClickable(false);
                break;
            case EMPTY_TYPE_GONE:
                this.setVisibility(View.GONE);
                break;
        }
        mEmptyType = type;
    }


    public int getEmptyType() {
        return mEmptyType;
    }

    public void setEmptyRes(int res) {
        mEmptyRes = res;
    }

    public int getEmptyRes() {
        return mEmptyRes;
    }

    public int getLoadingRes() {
        return mLoadingRes;
    }

    public void setLoadingRes(int loadingRes) {
        this.mLoadingRes = loadingRes;
    }

    public int getErrorRes() {
        return mErrorRes;
    }

    public void setErrorRes(int errorRes) {
        this.mErrorRes = errorRes;
    }

    /**
     * @return the mEmptyDrawableRes
     */
    public int getmEmptyDrawableRes() {
        return mEmptyDrawableRes;
    }

    /**
     * @param mEmptyDrawableRes the mEmptyDrawableRes to set
     */
    public void setmEmptyDrawableRes(int mEmptyDrawableRes) {
        this.mEmptyDrawableRes = mEmptyDrawableRes;
    }

    /**
     * @return the mErrorDrawableRes
     */
    public int getmErrorDrawableRes() {
        return mErrorDrawableRes;
    }

    /**
     * @param mErrorDrawableRes the mErrorDrawableRes to set
     */
    public void setmErrorDrawableRes(int mErrorDrawableRes) {
        this.mErrorDrawableRes = mErrorDrawableRes;
    }

}
