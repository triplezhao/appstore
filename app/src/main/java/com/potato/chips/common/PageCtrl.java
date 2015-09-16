package com.potato.chips.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.potato.demo.jiongtu.data.bean.JiongtuAlbum;
import com.potato.demo.jiongtu.ui.act.JiongTuDetailActivity;
import com.potato.library.util.L;


/**
 *
 * @ClassName: PageCtrl
 * @Description: 用于处理各模块的跳转
 * @date 2014-8-19 下午4:19:42
 */
public class PageCtrl {

    public static void startForResult(Context from, Class<?> clsAct,
                                      boolean finish, String action, Bundle data, int flags,
                                      int enterAnim, int exitAnim, int requestCode) {
        Intent intent = new Intent(from, clsAct);
        if (null != action) {
            intent.setAction(action);
        }
        if (null != data) {
            intent.putExtras(data);
        }
        if (0 != flags) {
            intent.setFlags(flags);
        }

        if (requestCode >= 0) {
            ((Activity) from).startActivityForResult(intent, requestCode);
        } else {
            from.startActivity(intent);
        }

        if (0 != enterAnim && 0 != exitAnim) {
            ((Activity) from).overridePendingTransition(enterAnim, exitAnim);
        }

        if (finish && from instanceof Activity) {
            ((Activity) from).finish();
        }
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data, int flags, int enterAnim, int exitAnim) {
        startForResult(from, clsAct, finish, action, data, flags, enterAnim,
                exitAnim, -1);
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data) {
        start(from, clsAct, finish, action, data, 0, 0, 0);
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data, int enterAnim, int exitAnim) {
        start(from, clsAct, finish, action, data, 0, enterAnim, exitAnim);
    }

    public static void startForResult(Context from, Class<?> clsAct,
                                      boolean finish, String action, Bundle data, int requestCode) {
        startForResult(from, clsAct, finish, action, data, 0, 0, 0, requestCode);
    }

    /**
     *
     * @Title: start2WebViewActivity
     * @Description: 跳转到webview界面
     * @date: 2014-8-11 下午4:06:19
     */
    public static void start2WebViewActivity(Context mContext,String address){
        L.d("dongxt", "start2WebViewActivity address = " + address);
        if(TextUtils.isEmpty(address))return;
        if (address.contains("http")) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.URL_ADDRESS, address);
            mContext.startActivity(intent);
        } else {
            // schema 处理
            if(address.contains("app.17173cucc") || address.contains("app.17173")){
                start2SchemaPage(mContext, address);
            }
        }
    }

    /**
     *
     * @Title: start2SchemaPage
     * @Description: 打开schemaHandlerActivity
     * @param: @param context
     * @param: @param String    设定文件
     * @return: void    返回类型
     * @date: 2014-10-20 下午3:45:48
     */
    public static void start2SchemaPage(Context context, String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
    /*
    EXTRA_ALBUM
    */
    public static void startJiongTuDetailActivity(Context context, JiongtuAlbum album){
        Bundle bundle = new Bundle();
        bundle.putSerializable(JiongTuDetailActivity.EXTRA_ALBUM, album);
        start(context,JiongTuDetailActivity.class,false,"",bundle);
    }

}

