package com.potato.library.net;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class Request {

    public static final int REQ_METHOD_GET = 1;
    public static final int REQ_METHOD_POST = 2;

    /**
     * 1.请求的标记tag。例如，在清除缓存的时候，会根据type值来决定是否清除这个请求的缓存。
     * 2.不同的请求，最终调用请求的基础方法不同，比如畅言，执行的畅言CyanClient.requestData()方法。
     */
    public String reqType = "0";
    /**
     * 可能是拼接处来的。比如：/rest/cont/strategy/channel/{0} 这里的参数不是?后面的。
     */
    public String url;
    /**
     * 请求的参数，放在url后面的参数
     */
    public Map<String, Object> params = new HashMap<String, Object>();
    /**
     * 请求的body
     */
    public String body;
    /**
     * post或者get等
     */
    public int reqMethod;

    public abstract void doRequest(Request request,
                                   AsyncHttpResponseHandler responseHandler,
                                   final RequestManager.DataLoadListener dataListener, final int cacheType,
                                   final int cacheTimeoutSeconds);

    /**
     * 返回这个请求的缓存 key
     */
    public abstract String getCacheKey();

}

