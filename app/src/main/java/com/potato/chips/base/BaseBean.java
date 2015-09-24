package com.potato.chips.base;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ztw on 2015/8/5.
 */
public class BaseBean {
    public static BaseBean creatFromJson(String json) {
        return null;
    }

    public static BaseBean createFromCursor(Cursor cursor) {
        return null;
    }

    public static BaseBean createFromJSON(JSONObject json) throws JSONException {
        return null;
    }
}
