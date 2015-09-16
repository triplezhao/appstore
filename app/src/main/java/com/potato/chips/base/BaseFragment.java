package com.potato.chips.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, Handler.Callback {
    /** extrars */
    /** views */
    /** adapters */
    /** data */
    /**
     * logic
     */
    public Handler mHandler = null;
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  If null, all callbacks and messages will be removed.
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


}
