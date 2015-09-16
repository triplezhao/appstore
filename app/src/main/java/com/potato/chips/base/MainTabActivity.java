package com.potato.chips.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.potato.demo.R;
import com.potato.demo.a.ui.act.AActivity;
import com.potato.demo.a.ui.act.BActivity;
import com.potato.demo.jiongtu.ui.act.JiongtuActivity;
import com.potato.demo.youku.ui.act.YKActivity;

public class MainTabActivity extends BaseTabHostActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected TabItem getTabItemView(int position) {

        TabItem tabItem = new TabItem();
        View tabItemView = mLayoutflater.inflate(R.layout.item_main_tab, null);
        ImageView iv_icon = (ImageView) tabItemView.findViewById(R.id.iv_icon);
        switch (position) {
            case 0:
                iv_icon.setImageResource(R.drawable.selector_nav_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), JiongtuActivity.class));
                break;

            case 1:

                iv_icon.setImageResource(R.drawable.selector_nav_explore);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), YKActivity.class));
                break;
            case 2:

                iv_icon.setImageResource(R.drawable.selector_nav_workout);

                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), AActivity.class));
                break;
            case 3:

                iv_icon.setImageResource(R.drawable.selector_nav_contact);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), BActivity.class));
                break;
            case 4:

                iv_icon.setImageResource(R.drawable.selector_nav_profile);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), BActivity.class));
                break;
        }

        return tabItem;
    }

    @Override
    protected int getTabItemCount() {
        return 5;
    }

    @Override
    public void onTabChanged(String s) {

    }
}
