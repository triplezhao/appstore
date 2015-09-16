package com.potato.library.net;

import android.content.Context;
import android.text.TextUtils;

import com.potato.library.util.NetUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

public class RequestHttpClient {
    private static Context mContext;
    public static AsyncHttpClient sHttpClient;
    public  String UA = "";

    private static final int TIMEOUT = 15 * 1000;
    private static final String CONTENTTYPE = "application/json";

    public static synchronized AsyncHttpClient getInstence(Context context) {
        if (sHttpClient == null) {
            mContext = context;
            sHttpClient = new AsyncHttpClient();
            sHttpClient.addHeader("Accept", CONTENTTYPE);
            sHttpClient.addHeader("Content-Type", CONTENTTYPE);
//            sHttpClient.setUserAgent(PhoneUtils.getDeviceUA(context));
            sHttpClient.setTimeout(TIMEOUT);
        }
        return sHttpClient;
    }

    public static void init(){

    }

    public static void request(String url, String bodyContent,
            AsyncHttpResponseHandler responseHandler, int method) {

        if (NetUtil.isNetworkAvailable(mContext)) {
            try {
                AsyncHttpClient httpClient = getInstence(mContext);
                if (httpClient == null) {
                    responseHandler.onFailure(new Throwable("c/k错误"),
                            "c/k错误,或者为 空");
                    return;
                }

                HttpEntity httpEntity = null;
                if (!TextUtils.isEmpty(bodyContent)) {
                    httpEntity = new StringEntity(bodyContent, HTTP.UTF_8);
                }

                if (method == Request.REQ_METHOD_GET) {
                    sHttpClient.get(url, responseHandler);
                } else if (method == Request.REQ_METHOD_POST) {
                    sHttpClient.post(null, url, httpEntity, CONTENTTYPE,
                            responseHandler);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                responseHandler.onFailure(new Throwable("对Entity转换字符串失败"),
                        "entity utf-8 UnsupportedEncodingException");
            }
        } else {
            // 表示网络被禁止
            responseHandler.onFailure(new HttpResponseException(9999,
                    "Network is Forbid 2G/3G,Wifi"),
                    "Network is Forbid 2G/3G,Wifi");
        }
    }

    public static void requestGet(String url, String bodyContent,
            AsyncHttpResponseHandler responseHandler) {
        request(url, bodyContent, responseHandler, Request.REQ_METHOD_GET);
    }

    public static void requestPost(String url, String bodyContent,
            AsyncHttpResponseHandler responseHandler) {
        request(url, bodyContent, responseHandler, Request.REQ_METHOD_POST);
    }

    /**
     * 囧图及下载应用使用此方法
     */
    public static void requestGet(String url,
            AsyncHttpResponseHandler responseHandler) {
        if (NetUtil.isNetworkAvailable(mContext)) {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.setTimeout(TIMEOUT * 3);
            httpClient.get(url, responseHandler);
        } else {
            L.d("Network not available, send fail message to response the request: "
                    + url);
            responseHandler.onFailure(new HttpResponseException(9999,
                    "Network is Forbid 2G/3G,Wifi"),
                    "Network is Forbid 2G/3G,Wifi");

        }
    }

}
