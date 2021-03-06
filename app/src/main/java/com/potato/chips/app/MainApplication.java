package com.potato.chips.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.util.FileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.PhoneUtils;
import com.potato.library.net.RequestHttpClient;
import com.potato.library.net.RequestManager;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zhaobingfeng on 14-12-22.
 */
public class MainApplication extends Application {
    /** 获取屏幕的宽和高 */
    public static int screenHight = 0;
    public static int screenWidth = 0;
    public static DisplayMetrics displayMetrices;
    /**
     * 设备的 IMEI
     */
    public static String IMEI = "";
    /**
     * 获取全局的上下文
     */
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    /**
     */
    private void init() {
        context = getApplicationContext();

        initDeviceWidthAndHeight();

        ShareSDK.initSDK(context);
        //获取imei
        PhoneUtils.getIMEI(context);
        //请求缓存管理
        RequestManager.init(context);
        //请求初始化
        AsyncHttpClient instence = RequestHttpClient.getInstence(context);
//        RequestConfig.addHttpClientRASHead(instence);
//        instence.setUserAgent(PhoneUtils.getDeviceUA(context));
//        initPicasso();
        initUIL();
        initDownloader();

    }


    private void initPicasso() {
//            final String imageCacheDir = /* 自定义目录 */ +"image";
//        Picasso picasso = new Picasso.Builder(context).memoryCache(
//                new LruCache(1024*1024*20)).downloader();
//            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttpDownloader(new File(imageCacheDir))).build();
//            Picasso.setSingletonInstance(picasso);
       /* File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = context.getExternalCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        Downloader downloader = new OkHttpDownloader(cacheDir, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);

        int memClass = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE))
                .getLargeMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;
        builder.memoryCache(new com.squareup.picasso.LruCache(cacheSize));

        Picasso picasso = builder.build();
        Picasso.setSingletonInstance(picasso);*/
    }

    public void initUIL(){
        ImageLoaderUtil.init(context);
    }

    /**
     * 获取设备的宽和高 WangQing 2013-8-12 void
     */
    private void initDeviceWidthAndHeight() {
        displayMetrices = PhoneUtils.getAppWidthAndHeight(this);
        screenHight = displayMetrices.heightPixels;
        screenWidth = displayMetrices.widthPixels;
    }

    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setDownloadDir(FileUtils.getDefaultDownloadDir(getApplicationContext()));
        configuration.setMaxThreadNum(10);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);

    }

    public static int SH = 800, SW = 480;
    private static boolean isScreenSizeInited = false;
    public static Point getSWAndSH(Context context) {
        if (!isScreenSizeInited || SW == 0) {
            Point pp = PhoneUtils.getRealDeviceResolution(context);
            SH = pp.y;
            SW = pp.x;
            isScreenSizeInited = true;
        }
        Point p = new Point(SW, SH);
        return p;
    }
}
