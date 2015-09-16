package com.potato.chips.base;

import android.text.TextUtils;

import java.util.Map;

public class BaseRequestBuilder {


    protected static void addParam(Map<String, Object> params, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            params.put(key, value);
        }
    }

    protected static void addParam(Map<String, Object> params, String key, int value) {
        if (value >= 0) {
            params.put(key, value);
        }
    }

    protected static void addParam(Map<String, Object> params, String key, long value) {
        if (value >= 0) {
            params.put(key, value);
        }
    }
}
