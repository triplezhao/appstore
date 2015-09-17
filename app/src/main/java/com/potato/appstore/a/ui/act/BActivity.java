package com.potato.appstore.a.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.potato.appstore.R;
import com.potato.appstore.databinding.ActivityBBinding;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.util.ImageLoaderUtil;

public class BActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show);
//        ImageView iv = (ImageView)findViewById(R.id.iv_icon);
        ActivityBBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_b);
        ImageLoaderUtil.displayImage("http://tvfan.kyodo.co.jp/wp-content/uploads/2015/01/15027b37a4104edd85fb5b79a6c9e3ac-344x516.jpg",binding.ivIcon, R.drawable.cheese_1);
    }

}
