package com.potato.chips.common;

import android.databinding.tool.util.L;
import android.text.TextUtils;

import com.potato.chips.app.MainApplication;
import com.potato.chips.util.PhoneUtils;
import com.potato.library.util.encrypt.PrivateUtil;
import com.potato.library.util.encrypt.SecurityUtil;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by ztw on 2015/6/30.
 */
public class RequestConfig {
    /**
     * 添加加密的头信息 WangQing 2013-8-28 void
     * @throws Exception
     */
    public static void addHttpClientRASHead(AsyncHttpClient httpClient) throws Exception {
        String k = "", c = "";
        String flag = MainApplication.IMEI;
        String currentTime = String.valueOf(System.currentTimeMillis());
        String random8Num = currentTime.substring((currentTime.length() - 8),
                currentTime.length());

        c = SecurityUtil.desEncode(flag, random8Num);

        String md5IMEI = SecurityUtil.encryptMD5(flag);

        String number16 = md5IMEI.substring(0, 16);
        StringBuffer sb = new StringBuffer();
        sb.append(random8Num + number16);
        k = SecurityUtil.rsaEncode(sb.toString(), PrivateUtil.publicKey);

        String ua = PhoneUtils.getDeviceUA(MainApplication.context);
        httpClient.addHeader("c", c.trim());
        httpClient.addHeader("k", k.trim().replace("\n", ""));
        httpClient.addHeader("v", "3.0");
        httpClient.addHeader("i", ua);

        L.d("17173", "Init request header, C:" + c + "\nK:" + k + "\nV: 3.0\ni:" + ua);
        if (TextUtils.isEmpty(c)|| TextUtils.isEmpty(k)) {
            throw new Exception();
        }
    }
}
