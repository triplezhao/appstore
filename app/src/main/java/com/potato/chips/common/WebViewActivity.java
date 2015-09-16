package com.potato.chips.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.potato.demo.R;
import com.potato.chips.util.PhoneUtils;
import com.potato.chips.util.UIUtils;
import com.potato.library.util.L;
import com.potato.library.util.NetUtil;
import com.potato.library.view.NormalEmptyView;

import java.util.Timer;

/**
 * 内置浏览器页面
 *
 * @author
 * @time
 */
public class WebViewActivity extends Activity implements OnClickListener {

    public static final String TAG = "WebViewActivity";
    /**
     * URL地址
     */
    public static final String URL_ADDRESS = "URL_ADDRESS";
    /**
     * title标题
     */
    public static final String TITLE = "title";
    private static final int TIMEOUTFLAG = 1;
    protected boolean isRefresh = false;// 判断是否刷新
    /**
     * 默认的活动页面
     */
    private String defaultURL = "http://baidu.com";
    /**
     * 地址
     */
    private String url_Address;
    private long timeout = 10000;
    private WebView webView;
    private NormalEmptyView not_app;
    private ImageView iv_close;

    protected Context mContext = null;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TIMEOUTFLAG)
                notConnect();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
        mContext = this;
        String url = getIntent().getStringExtra(URL_ADDRESS);
        if (TextUtils.isEmpty(url_Address)) url_Address = defaultURL;
        // "http://10.6.212.211/app3.0/app3.0_activity/downTest.html";
        // 启动默认活动页面的时候后面加上apptoken
        setContentView(R.layout.activity_webview);
        initViews();
        initData();
    }


    void initViews() {
        // init the title


        webView = (WebView) findViewById(R.id.webView);
        not_app = (NormalEmptyView) findViewById(R.id.empty_view);

        iv_close = (ImageView) findViewById(R.id.iv_close);
//        refreshIv = (ImageView) findViewById(R.id.refreshIv);

        iv_close.setOnClickListener(this);

        not_app.setOnClickListener(this);
        not_app.setEmptyType(NormalEmptyView.EMPTY_TYPE_LOADING);

        initWebView();
    }

    void initData() {
        initWebViewForHTML5();
//        webView.addJavascriptInterface(this, "webGetAndroidData");// js调用本地方法
        webView.setWebViewClient(new WebViewClient() {
            private Timer timer;

            @Override
            public void doUpdateVisitedHistory(WebView view, String url,
                                               boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                iv_close.setEnabled(webView.canGoBack());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                iv_close.setEnabled(webView.canGoBack());

                not_app.setVisibility(View.VISIBLE);
                webView.setBackgroundColor(getResources().getColor(
                        R.color.app_bg));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.d(TAG, " onPageFinished url  = " + url);
                if (NetUtil.isNetworkAvailable(mContext))
                    not_app.setVisibility(View.GONE);
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 活动界面进入跳进SchemaHandlerActivity 处理页面
                L.d(TAG, " shouldOverrideUrlLoading url  = " + url);
                if (url.contains("app.17173cucc") || url.contains("app.17173")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.putExtra("url", url_Address);
                    startActivity(intent);
                    return true;
                }

                // http://10.6.212.211/app3.0/app3.0_activity/app17173.activity.subscribeAnchor?subscribe=yes&anchorid=33607631&callback=changeSubscribeState
                if (!PhoneUtils.isNetworkAvailable(WebViewActivity.this)) {
                    notConnect();
                    return true;
                }
//                TCAgent.onPageStart(null, "活动二级页");
                view.loadUrl(url);
                return true;
            }
        });

    }


    /**
     * 网络没有连接
     */
    private void notConnect() {
        UIUtils.toast(mContext, R.string.potato_no_net);
//        ToastUtil.showMessageText(this,
//                getResources().getString(R.string.no_net));
        not_app.setVisibility(View.VISIBLE);
        not_app.setErrorRes(R.string.potato_loading);
        not_app.setEmptyType(NormalEmptyView.EMPTY_TYPE_ERROR);
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        webView.setVerticalScrollBarEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.NORMAL);
        webView.setHorizontalScrollBarEnabled(true);// 禁用水平滚动条
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);// 设置可以运行javascript，这样就可以播放gif了，且可以加载图片
        webView.setBackgroundColor(getResources().getColor(R.color.potato_trans));

        webView.loadUrl(url_Address);
    }

    /**
     * 因为wap版中使用了html5的localstorage,webview默认是不支持的，所以，需要在web的设置中加入这段代码
     */
    private void initWebViewForHTML5() {
        // 启用数据库
        webView.getSettings().setDatabaseEnabled(true);
        String dir = this.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        // 设置数据库路径
        webView.getSettings().setDatabasePath(dir);
        // 使用localStorage则必须打开
        webView.getSettings().setDomStorageEnabled(true);
        // 扩充数据库的容量（在WebChromeClinet中实现）
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onExceededDatabaseQuota(String url,
                                                String databaseIdentifier, long quota,
                                                long estimatedDatabaseSize, long totalQuota,
                                                QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }
        });
    }

    /**
     * 对网址校验
     *
     * @param urlAddress
     */
    private void loadURL(String urlAddress) {
        if (!TextUtils.isEmpty(urlAddress)) {
//            TestUtils.logI("要浏览的地址是：" + urlAddress);
            // if (checkURL(urlAddress)) {
            webView.loadUrl(urlAddress);
            // } else {
            // ToastUtil.showMessageText(this, "当前网址不可用");
            // }
        } else {
            UIUtils.toast(mContext, R.string.potato_no_net);
//            ToastUtil.showMessageText(this, "网址异常");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.empty_view) {
            refresh();
        } else if (id == R.id.iv_close) {
            finish();
        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (!PhoneUtils.isNetworkAvailable(this)) {
            notConnect();
            return;
        }

        not_app.setOnClickListener(this);
        not_app.setEmptyType(NormalEmptyView.EMPTY_TYPE_LOADING);

        String url = "";
        if (TextUtils.isEmpty(webView.getUrl())) {
            url = url_Address;
        } else {
            url = webView.getUrl();
        }
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        // 针对首页广告处理，启动到MainActivity
//        if (CATE_SPLASH_AD.equals(category)) {
//            startMainActivity();
//            return;
//        }

//        if (CATE_PUSH.equals(category)) {
//            PhoneUtils.goBackOperate(this);
//            return;
//        }

        super.onBackPressed();
    }

    //手机返回按键 不单独处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 返回前一个页面
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
