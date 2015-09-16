package com.potato.chips.base;

import android.view.View;

public class BaseViewHolder {
    public View view;

    public BaseViewHolder(View itemView) {
        this.view = itemView;
        view.setTag(this);
    }

}