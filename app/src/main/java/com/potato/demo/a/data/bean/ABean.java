package com.potato.demo.a.data.bean;

/**
 * Created by ztw on 2015/7/6.
 */
public class ABean {
    private String title;
    private String content;
    private String icon;

    public ABean(String title, String icon, String conent) {
        this.title = title;
        this.icon = icon;
        this.content = conent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
