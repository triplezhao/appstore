package com.potato.demo.jiongtu.data.bean;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 囧图王-图册
 * 
 * @author zhaobingfeng
 */
public class JiongtuAlbum implements Serializable {
    private static final long serialVersionUID = -1552323434474377850L;
    public static final String TYPE = "PHOTOS";
	/** 数据库中ID */
	private long _id = -1;
	/** 图册ID */
	private long id;
	/** 所属栏目ID */
	private long sectionId;
	private boolean hot;
	/** 是否最新 */
	private boolean New;
	/** 图册标题 */
	private String title;
	private boolean review;
	/** 封面-中图 */
	private String middleCover;
	/** 封面-大图 */
	private String bigCover;
	/** 图册拥有图片数量 */
	private int count;
	private int viewCount;
	private int rantCount;
	private int commentCount;
	/** 顶的数量 */
	private int dingCount;
	/** 踩的数量 */
	private int caiCount;
	/** 编辑来源 */
	private String editor;
	/** 发布时间 */
	private long publicDate;
	/** 是否已读 */
	private boolean havaRead;
	/** 上次更新时间 */
	private long lastUpdateTime;
	/** 所属栏目标题 */
	private String sectionTitle;
	/** 是否有封面 */
	private boolean hasCover;
	/** 选择状态 */
	private boolean isSelectedState;
	/** 是否为广告 */
    private boolean isAd = false;

	private String imageData;


	public boolean isHasCover() {
		return hasCover;
	}

	public void setHasCover(boolean hasCover) {
		this.hasCover = hasCover;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isNew() {
		return New;
	}

	public void setNew(boolean new1) {
		New = new1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isReview() {
		return review;
	}

	public void setReview(boolean review) {
		this.review = review;
	}

	public String getMiddleCover() {
		return middleCover;
	}

	public void setMiddleCover(String middleCover) {
		this.middleCover = middleCover;
	}

	public String getBigCover() {
		return bigCover;
	}

	public void setBigCover(String bigCover) {
		this.bigCover = bigCover;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getRantCount() {
		return rantCount;
	}

	public void setRantCount(int rantCount) {
		this.rantCount = rantCount;
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

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public long getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(long publicDate) {
		this.publicDate = publicDate;
	}

	public boolean isHaveRead() {
		return havaRead;
	}

	public void setHaveRead(boolean havaRead) {
		this.havaRead = havaRead;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public boolean isSelectedState() {
		return isSelectedState;
	}

	public void setSelectedState(boolean isSelectedState) {
		this.isSelectedState = isSelectedState;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	/**
     * @return the iaAd
     */
    public boolean isAd() {
        return isAd;
    }

    /**
     * @param  isAd the iaAd to set
     */
    public void setIsAd(boolean isAd) {
        this.isAd = isAd;
    }

	public static JiongtuAlbum createFromJSON(JSONObject json) {
		if (json == null) {
			return null;
		}
		JiongtuAlbum a = new JiongtuAlbum();
		try {
			a.setId(json.optLong("ID"));
			if (!json.isNull("Section")) {
				JSONObject jo = json.getJSONObject("Section");
				if (jo != null) {
					a.setSectionId(jo.optLong("ID"));
				}
			}
			a.setHot(json.optBoolean("Hot"));
			a.setNew(json.optBoolean("New"));
			a.setTitle(json.optString("Title"));
			a.setReview(json.optBoolean("Review"));
			a.setHasCover(true);
			String middleCover =json.optString("MiddleCover");
			String bigCover =json.optString("BigCover");
			if((TextUtils.isEmpty(middleCover)||middleCover.equalsIgnoreCase("null"))&&(TextUtils.isEmpty(bigCover)||bigCover.equalsIgnoreCase("null"))){//没有封面
				a.setHasCover(false);
			}
			a.setMiddleCover(middleCover);
			a.setBigCover(bigCover);
			a.setCount(json.optInt("Count"));
			a.setViewCount(json.optInt("ViewCount"));
			a.setRantCount(json.optInt("RantCount"));
			a.setCommentCount(json.optInt("CommentCount"));
			a.setDingCount(json.optInt("DingCount"));
			a.setCaiCount(json.optInt("CaiCount"));
			a.setEditor(json.optString("Editor"));
			a.setPublicDate(json.optLong("PublicDate"));
			a.setHaveRead(json.optBoolean("HaveRead"));
			a.setLastUpdateTime(json.optLong("LastUpdateTime"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return a;
	}


}
