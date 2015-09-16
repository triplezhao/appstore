package com.potato.demo.jiongtu.data.bean;

import org.json.JSONObject;

public class JiongtuSection {
    private long _id = -1;
    /**
     * 栏目ID
     */
    private long sectionId;
    /**
     * 栏目名称
     */
    private String title; // 栏目名称
    /**
     * 栏目图标URL
     */
    private String icon;
    /**
     * 是否已订阅，true表示已订阅，false表示未订阅，为空表示未知（没有登录时）
     */
    private Boolean isEnabled;
    /**
     * 是否最新标记
     */
    private Boolean isNew;
    /**
     * 是否推荐标记
     */
    private Boolean isRecommended;
    /**
     * 是否栏目被选中
     */
    private Boolean isChoosed = false;
    /**
     * 是否常驻
     */
    private Boolean isFixed;
    /**
     * 是否默认栏目
     */
    private Boolean isDefaultSection;
    /**
     * 最后更新时间
     */
    private long lastUpateTime;
    /**
     * 订阅时间
     */
    private long subscripeTime;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Boolean getIsChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(Boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public Boolean getIsDefaultSection() {
        return isDefaultSection;
    }

    public void setIsDefaultSection(Boolean isDefaultSection) {
        this.isDefaultSection = isDefaultSection;
    }

    public long getLastUpateTime() {
        return lastUpateTime;
    }

    public void setLastUpateTime(long lastUpateTime) {
        this.lastUpateTime = lastUpateTime;
    }

    public long getSubscripeTime() {
        return subscripeTime;
    }

    public void setSubscripeTime(long subscripeTime) {
        this.subscripeTime = subscripeTime;
    }


    public static JiongtuSection createFromJSON(JSONObject json) {
        if (json == null) {
            return null;
        }
        JiongtuSection section = new JiongtuSection();
        section.setSectionId(json.optLong("ID"));
        section.setTitle(json.optString("Title"));
        section.setIcon(json.optString("Icon"));
        section.setIsFixed(json.optBoolean("Fixed"));
        section.setIsDefaultSection(json.optBoolean("Default"));
        section.setIsNew(json.optBoolean("New"));
        section.setIsRecommended(json.optBoolean("Recommended"));
        section.setIsEnabled(json.optBoolean("Enabled", false));
        section.setLastUpateTime(json.optLong("LastUpdateTime"));
        return section;
    }

    @Override
    public String toString() {
        return "Section [_id=" + _id + ", sectionId=" + sectionId + ", title="
                + title + ", icon=" + icon + ", isEnabled=" + isEnabled
                + ", isNew=" + isNew + ", isRecommended=" + isRecommended
                + ", isChoosed=" + isChoosed + ", isFixed=" + isFixed
                + ", isDefaultSection=" + isDefaultSection + ", lastUpateTime="
                + lastUpateTime + ", subscripeTime=" + subscripeTime + "]";
    }
}
