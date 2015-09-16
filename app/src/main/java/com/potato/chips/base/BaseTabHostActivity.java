package com.potato.chips.base;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public abstract class BaseTabHostActivity extends TabActivity implements OnTabChangeListener {

	private TabHost mTabHost;
	private TabWidget mTabWidget;
	protected LayoutInflater mLayoutflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLayoutflater = getLayoutInflater();

		mTabHost = getTabHost();
		mTabWidget = getTabWidget();
		//mTabWidget.setStripEnabled(false);	// need android2.2

		onPprepare();
		initTabSpec();
		mTabHost.setOnTabChangedListener(this);
	}

	private void initTabSpec() {

		int count = getTabItemCount();

		for (int i = 0; i < count; i++) {
			// set text view

//			TextView tvTabItem = (TextView) tabItem.findViewById(R.id.tab_item_tv);
			TabItem tabItem = getTabItemView(i);
			// set id
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItem.getTitle());
			tabSpec.setIndicator(tabItem.getView());
			tabSpec.setContent(tabItem.getIntent());

			mTabHost.addTab(tabSpec);
		}

	}

	/** 在初始化界面之前调用 */
	protected void onPprepare() {
		// do nothing or you override it
	}


	protected int getTabCount() {
		return mTabHost.getTabWidget().getTabCount();
	}

	/** 设置TabItem的图标和标题等*/
//	abstract protected void setTabItemTextView(TextView textView, int position);
	abstract protected TabItem getTabItemView(int position);

	abstract protected int getTabItemCount();

	protected void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}
	protected int getCurrentTab(){
		return mTabHost.getCurrentTab();
	}
	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
	}



	class TabItem {
		private String title;        // tab item title
		private int icon;            // tab item icon
		private Intent intent;    // tab item intent

		public View view;

		public TabItem() {
		}

		public TabItem(String title, int icon, Intent intent) {
			super();
			this.title = title;
			this.icon = icon;
			this.intent = intent;
		}


		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public Intent getIntent() {
			return intent;
		}

		public void setIntent(Intent intent) {
			this.intent = intent;
		}

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}
	}

}