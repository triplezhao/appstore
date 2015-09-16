package com.potato.library.net;

import android.text.TextUtils;

import com.potato.library.net.RequestManager.DataLoadListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ztw tianwuzhao@cyou-inc.com
 * @ClassName: DefaultRequest
 * @Description: TODO(默认的请求类。用户可以基础，重写请求方法，和key过滤方法。这里过滤方法去掉了vts等参数，给173用。)
 * @date 2015-4-10 下午6:50:38
 */
public class DefaultRequest extends Request {

    private static final String TAG = "DefaultRequest";
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
    /**
     * 标记是否需要激活才能发出请求 ，默认是，如果想默认为否，requestBuilder方法中可以配置。或者基础request，修改默认值
     */
    public boolean needActive = true;

    /**
     * 返回这个请求的缓存 key
     */
    @Override
    public String getCacheKey() {
        // url+过滤过的参数列表
        int urlHash = getUrlHash();
        int bodyHash = getBodyHash();

        String cacheKey = urlHash + "_" + bodyHash;

        return cacheKey;
    }

    @Override
    public void doRequest(final Request request,
                          final AsyncHttpResponseHandler responseHandler,
                          final DataLoadListener dataListener, final int cacheType,
                          final int cacheTimeoutSeconds) {
        // TODO Auto-generated method stub
        RequestHttpClient.request(
                RequestUtil.getParamedUrl(DefaultRequest.this, null),
                request.body, responseHandler, request.reqMethod);
    }

    public int getUrlHash() {
        // TODO Auto-generated method stub
        int hash = -1;
        String url = RequestUtil.getParamedUrl(DefaultRequest.this,
                RequestUtil.getCommonFilteredParams());
        if (!TextUtils.isEmpty(url)) {
            hash = url.hashCode();
        }
        return hash;
    }

    public int getBodyHash() {
        // TODO Auto-generated method stub
        int hash = -1;
        String jsonStr = null;

        if (!TextUtils.isEmpty(body)) {
            try {
                JSONObject json = new JSONObject(body);
                if (json.has("cts")) {
                    json.remove("cts");
                }
                if (json.has("ccts")) {
                    json.remove("ccts");
                }
                if (json.has("vts")) {
                    json.remove("vts");
                }
                jsonStr = json.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(jsonStr)) {
                hash = jsonStr.hashCode();
            }
        }
        return hash;
    }
}
