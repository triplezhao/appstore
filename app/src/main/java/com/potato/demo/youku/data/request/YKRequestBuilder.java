package com.potato.demo.youku.data.request;

import android.text.TextUtils;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;

import java.util.HashMap;

public class YKRequestBuilder extends BaseRequestBuilder {

    /**
     *http://open.youku.com/docs?id=49
     *除了两个id，其他都可以给"";
     * openapi.youku.com/v2/videos/by_user.json?client_id=e4da0658e508bb69&user_name=superhebefans
     */
    public static Request videosByUser(String client_id, String user_id,String user_name,String orderby,
                                       String page, String count,String last_item) {
        Request req = new DefaultRequest();
        req.url = YKRequestUrls.videos_by_user;
        req.reqMethod = Request.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "client_id", client_id);

        if (!TextUtils.isEmpty(user_id)) {
            addParam(req.params, "user_id", user_id);
        }
        if (!TextUtils.isEmpty(user_name)) {
            addParam(req.params, "user_name", user_name);
        }
        if (!TextUtils.isEmpty(orderby)) {
            addParam(req.params, "orderby", orderby);
        }
        if (!TextUtils.isEmpty(page)) {
            addParam(req.params, "page", page);
        }
        if (!TextUtils.isEmpty(count)) {
            addParam(req.params, "count", count);
        }
        if (!TextUtils.isEmpty(last_item)) {
            addParam(req.params, "last_item", last_item);
        }
        return req;
    }

}
