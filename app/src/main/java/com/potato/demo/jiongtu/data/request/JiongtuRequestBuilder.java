package com.potato.demo.jiongtu.data.request;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;

import java.util.HashMap;

public class JiongtuRequestBuilder extends BaseRequestBuilder {

    /**
     * 秀场-手机首页列表
     */
    // 手机首页列表 http://show.17173.com/m/index.action?uct=2&hct=1
    public static Request getShowIndexRequest(String uct, String hct) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_GET;
        request.url = JiongtuRequestUrls.URL_TEST;

        request.params = new HashMap<String, Object>();
        addParam(request.params, "uct", uct);
        addParam(request.params, "hct", hct);
        return request;
    }


    /**
     * 囧图图册列表请求
     *
     * @param sectionId     囧图栏目ID
     * @param minPublicDate 本地已有数据的最早更新时间
     */
    public static Request getAlbumListRequest(long sectionId, long minPublicDate) {
        Request req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_ALBUM_LIST;
        req.reqMethod = Request.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        if (minPublicDate > 0) {
            addParam(req.params, "maxPublicDate", minPublicDate);
        }
        addParam(req.params, "sections", sectionId);
        return req;
    }

    /**
     * 囧图栏目列表请求
     */
    public static Request getSectionListRequest() {
        Request req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_SECTION_LIST;
        req.reqMethod = Request.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        req.params.put("pageSize", -1);
        return req;
    }

    /**
     * 囧图图册下图片列表请求
     *
     * @param albumId 图册ID
     */
    public static Request getPhotoListRequest(String albumId) {
        Request req = new DefaultRequest();
        req.url = JiongtuRequestUrls.URL_JIONG_PHOTO_LIST;
        req.reqMethod = Request.REQ_METHOD_GET;
        req.params = new HashMap<String, Object>();

        addParam(req.params, "platCode", "ANDROID");
        addParam(req.params, "photosID", albumId);
        return req;
    }
}
