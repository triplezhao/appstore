package com.potato.chips.base;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * @author ztw 这个类只提供基础的解析方法，每个接口对应的解析方法在.parse.api包下面。
 */
public abstract class BaseParser {
    public JSONObject root;
    // 错误信息
    public String code = ""; // 失败Code示例100101 返回code组成：1失败/成功 001 模块ID 01方法
    public String msg = ""; // "succ", //失败fail
    public boolean succ = false; // "succ", //失败fail

    public BaseParser(String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }
        try {
            root = new JSONObject(jsonStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public abstract String getCode();

    public abstract String getMsg();

    public abstract boolean isSucc();

    ;

}
