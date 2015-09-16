package com.potato.demo.jiongtu.data.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 囧图王-图片
 *
 * @author zhaobingfeng
 */
public class JiongtuPhoto implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;

    private int type; // 图片类型，1-静态图 2-动态图（如：GIF，目前只支持GIF）3-视频

    private int indexOrder; // 图片索引

    private String title; // 图片标题

    private String content; // 图片内容

    private String smallUrl; // 小尺寸图片链接
    private String middleUrl; // 中尺寸图片链接
    private int middleWidth; // 中尺寸宽度像素
    private int middleHeight; // 中尺寸高度像素
    private String bigUrl; // 大尺寸图片链接
    private int bigWidth; // 大尺寸图片宽度像素
    private int bigHeight; // 大尺寸图片高度像素
    private String originalUrl; // 原始图链接，若类型是gif或者视频，则链接放在此处
    private String videoCoverUrl; // 视频截图链接
    private int viewCount; // 图片总浏览数
    private int commentCount; // 图片总评论数
    private int dingCount; // 图片总顶数
    private int caiCount; // 图片总踩数
    private int shareCount; // 图片总分享数
    private Long lastUpdateTime; // 最后更新时间戳
    private Long albumId; // 图册ID
    private String contentText; // HTML转义后的内容
    private String service;//游戏区服
    private String nickName;//游戏昵称
    private String society;//所在公会

    public static JiongtuPhoto createFromJSON(JSONObject json) {
        if (json == null) {
            return null;
        }
        JiongtuPhoto p = new JiongtuPhoto();
        p.setId(json.optLong("ID"));
        p.setType(json.optInt("Type"));
        p.setIndexOrder(json.optInt("IndexOrder"));
        p.setTitle(parseNullText(json.optString("Title", "null")));
        p.setContent(parseNullText(json.optString("Content", "null")));
        p.setContentText(parseNullText(json.optString("ContentText", "null")));
        p.setMiddleUrl(json.optString("MiddleUrl"));
        p.setMiddleHeight(json.optInt("MiddleHeight", 0));
        p.setMiddleWidth(json.optInt("MiddleWidth", 0));
        p.setBigUrl(json.optString("BigUrl"));
        p.setBigHeight(json.optInt("BigHeight", 0));
        p.setBigWidth(json.optInt("BigWidth", 0));
        p.setOriginalUrl(json.optString("OriginalUrl"));
        p.setVideoCoverUrl(json.optString("VideoCoverUrl"));
        p.setViewCount(json.optInt("ViewCount"));
        p.setCommentCount(json.optInt("CommentCount"));
        p.setDingCount(json.optInt("DingCount"));
        p.setCaiCount(json.optInt("CaiCount"));
        p.setShareCount(json.optInt("ShareCount"));
        p.setLastUpdateTime(json.optLong("LastUpdateTime"));
        p.setAlbumId(json.optLong("PhotosID"));
        return p;
    }

    public static JiongtuPhoto createFromStrategyJSON(JSONObject json) {
        if (json == null) {
            return null;
        }
        JiongtuPhoto p = new JiongtuPhoto();
        p.setId(json.optLong("id"));
        p.setBigUrl(json.optString("bigUrl"));
        p.setSmallUrl(json.optString("smallUrl"));
        p.setOriginalUrl(json.optString("orginUrl"));
        p.setAlbumId(json.optLong("picGroupId"));
        return p;
    }

    private static String parseNullText(String str) {
        if (str.equalsIgnoreCase("null")) {
            return "";
        } else {
            return str;
        }
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(int indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMiddleUrl() {
        return middleUrl;
    }

    public void setMiddleUrl(String middleUrl) {
        this.middleUrl = middleUrl;
    }

    public int getMiddleWidth() {
        return middleWidth;
    }

    public void setMiddleWidth(int middleWidth) {
        this.middleWidth = middleWidth;
    }

    public int getMiddleHeight() {
        return middleHeight;
    }

    public void setMiddleHeight(int middleHeight) {
        this.middleHeight = middleHeight;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public int getBigWidth() {
        return bigWidth;
    }

    public void setBigWidth(int bigWidth) {
        this.bigWidth = bigWidth;
    }

    public int getBigHeight() {
        return bigHeight;
    }

    public void setBigHeight(int bigHeight) {
        this.bigHeight = bigHeight;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getVideoCoverUrl() {
        return videoCoverUrl;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        this.videoCoverUrl = videoCoverUrl;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getDingCount() {
        return dingCount;
    }

    public void setDingCount(int dingCount) {
        this.dingCount = dingCount;
    }

    public int getCaiCount() {
        return caiCount;
    }

    public void setCaiCount(int caiCount) {
        this.caiCount = caiCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
