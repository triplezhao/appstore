package com.potato.chips.util;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

import com.potato.demo.R;
import com.potato.chips.app.MainApplication;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ztw on 2015/8/3.
 */
public class ImageLoaderUtil {

    private static DisplayImageOptions def_options;

    public static void init(Context context){
        def_options= getDefOptionsBuilder().build();
        ImageLoaderConfiguration defConfig = getDefConfig(context);
        ImageLoader.getInstance().init(defConfig);
    }

    public static void displayImage(String url,ImageView view){
        ImageLoader.getInstance().displayImage(url, view, def_options);
    }

    public static void displayImage(String url,ImageView view,int imageRes){
        DisplayImageOptions.Builder optionsBuider = getDefOptionsBuilder();
        optionsBuider.showImageOnLoading(imageRes); // resource or drawable
        optionsBuider.showImageForEmptyUri(imageRes); // resource or drawable
        optionsBuider.showImageOnFail(imageRes); // resource or drawable
        ImageLoader.getInstance().displayImage(url,view,optionsBuider.build());
    }
    public static void displayImage(String url,ImageView view,int imageRes,int w,int h){
        //改变图片宽高还没实现。。。等195版本的UIL
        //只能在config里设置全局的最大宽高
        displayImage(url, view, imageRes);
    }
    public static void displayImage(String url,ImageView view,DisplayImageOptions options){
        ImageLoader.getInstance().displayImage(url, view, options);
    }







    public static DisplayImageOptions.Builder getDefOptionsBuilder(){
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_launcher) // resource or drawable
                .showImageOnFail(R.drawable.ic_launcher) // resource or drawable
                .cacheInMemory(true) // default false
                .cacheOnDisk(true) // default false
//                .resetViewBeforeLoading(false)  // default
//                .delayBeforeLoading(1000)
//                .preProcessor(new BitmapProcessor() {
//
//                    @Override
//                    public Bitmap process(Bitmap bitmap) {
//                        return null;
//                    }
//                })
//                .postProcessor(new BitmapProcessor() {
//
//                    @Override
//                    public Bitmap process(Bitmap bitmap) {
//                        return null;
//                    }
//                })
//                .extraForDownloader(null)
//                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.NONE) // default
//                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
//                .decodingOptions(new BitmapFactory.Options());
//                .displayer(new SimpleBitmapDisplayer()) // default
//                .handler(new Handler())// default*/
                ;
    }

    public  static  ImageLoaderConfiguration getDefConfig(Context context){
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        File cacheDir=StorageUtils.getIndividualCacheDirectory(context);
        LruDiskCache diskcache = null;
        try {
            diskcache = new LruDiskCache(cacheDir,new Md5FileNameGenerator(), 500 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int memClass = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE))
                .getLargeMemoryClass();
        long availableMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int)availableMemory / 4;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(MainApplication.screenWidth, MainApplication.screenHight) // default = device screen dimensions
//                .diskCacheExtraOptions(MainApplication.screenWidth*10, MainApplication.screenHight * 10, null)//设置了超过屏幕，不起作用，最大就到屏幕宽高，不设置，就会是原图。
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSizePercentage(30) // default
                .diskCache(diskcache) // default
                .diskCacheFileCount(1000)
                .writeDebugLogs()
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 2) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCache(new LruMemoryCache(cacheSize))
//                .memoryCacheSize(cacheSize)
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .build();
        return config;
    }
}
