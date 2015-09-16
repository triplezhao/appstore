package com.potato.demo.youku.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.demo.youku.data.db.YKVideoProvider;
import com.potato.chips.base.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class YKVideo extends BaseBean implements Serializable {

    //propslist
    private String comment_count;
    private String link;
    private String state;
    private String operation_limit;
    private String isinteract;
    private String id;
    private String down_count;
    private String title;
    private String duration;
    private String category;
    private String thumbnail;
    private String up_count;
    private String favorite_count;
    private String public_type;
    private String view_count;
    private String published;
    private String streamtypes;

    //set get list
    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getComment_count() {
        return this.comment_count;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return this.link;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setOperation_limit(String operation_limit) {
        this.operation_limit = operation_limit;
    }

    public String getOperation_limit() {
        return this.operation_limit;
    }

    public void setIsinteract(String isinteract) {
        this.isinteract = isinteract;
    }

    public String getIsinteract() {
        return this.isinteract;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setDown_count(String down_count) {
        this.down_count = down_count;
    }

    public String getDown_count() {
        return this.down_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setUp_count(String up_count) {
        this.up_count = up_count;
    }

    public String getUp_count() {
        return this.up_count;
    }

    public void setFavorite_count(String favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getFavorite_count() {
        return this.favorite_count;
    }

    public void setPublic_type(String public_type) {
        this.public_type = public_type;
    }

    public String getPublic_type() {
        return this.public_type;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getView_count() {
        return this.view_count;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getPublished() {
        return this.published;
    }

    public void setStreamtypes(String streamtypes) {
        this.streamtypes = streamtypes;
    }

    public String getStreamtypes() {
        return this.streamtypes;
    }


    //other
    //createFromCursor
    public static YKVideo createFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        YKVideo bean = new YKVideo();
        bean.setComment_count(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.comment_count)));
        bean.setLink(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.link)));
        bean.setState(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.state)));
        bean.setOperation_limit(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.operation_limit)));
        bean.setIsinteract(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.isinteract)));
        bean.setId(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.id)));
        bean.setDown_count(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.down_count)));
        bean.setTitle(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.title)));
        bean.setDuration(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.duration)));
        bean.setCategory(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.category)));
        bean.setThumbnail(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.thumbnail)));
        bean.setUp_count(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.up_count)));
        bean.setFavorite_count(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.favorite_count)));
        bean.setPublic_type(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.public_type)));
        bean.setView_count(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.view_count)));
        bean.setPublished(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.published)));
        bean.setStreamtypes(cursor.getString(cursor.getColumnIndex(YKVideoProvider.Columns.streamtypes)));
        return bean;
    }

    //createFromJSON
    public static YKVideo createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        YKVideo bean = new YKVideo();
        bean.setComment_count(json.optString("comment_count"));
        bean.setLink(json.optString("link"));
        bean.setState(json.optString("state"));
        bean.setOperation_limit(json.optString("operation_limit"));
        bean.setIsinteract(json.optString("isinteract"));
        bean.setId(json.optString("id"));
        bean.setDown_count(json.optString("down_count"));
        bean.setTitle(json.optString("title"));
        bean.setDuration(json.optString("duration"));
        bean.setCategory(json.optString("category"));
        bean.setThumbnail(json.optString("thumbnail"));
        bean.setUp_count(json.optString("up_count"));
        bean.setFavorite_count(json.optString("favorite_count"));
        bean.setPublic_type(json.optString("public_type"));
        bean.setView_count(json.optString("view_count"));
        bean.setPublished(json.optString("published"));
        bean.setStreamtypes(json.optString("streamtypes"));
        return bean;
    }

    //createFromJSONArray
    public static ArrayList<YKVideo> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<YKVideo> list = new ArrayList<YKVideo>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            YKVideo entity = YKVideo.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }

    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        YKVideo bean = new YKVideo();

        if (baseBean != null) {
            bean = (YKVideo) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(YKVideoProvider.Columns.comment_count, bean.getComment_count());
        values.put(YKVideoProvider.Columns.link, bean.getLink());
        values.put(YKVideoProvider.Columns.state, bean.getState());
        values.put(YKVideoProvider.Columns.operation_limit, bean.getOperation_limit());
        values.put(YKVideoProvider.Columns.isinteract, bean.getIsinteract());
        values.put(YKVideoProvider.Columns.id, bean.getId());
        values.put(YKVideoProvider.Columns.down_count, bean.getDown_count());
        values.put(YKVideoProvider.Columns.title, bean.getTitle());
        values.put(YKVideoProvider.Columns.duration, bean.getDuration());
        values.put(YKVideoProvider.Columns.category, bean.getCategory());
        values.put(YKVideoProvider.Columns.thumbnail, bean.getThumbnail());
        values.put(YKVideoProvider.Columns.up_count, bean.getUp_count());
        values.put(YKVideoProvider.Columns.favorite_count, bean.getFavorite_count());
        values.put(YKVideoProvider.Columns.public_type, bean.getPublic_type());
        values.put(YKVideoProvider.Columns.view_count, bean.getView_count());
        values.put(YKVideoProvider.Columns.published, bean.getPublished());
        values.put(YKVideoProvider.Columns.streamtypes, bean.getStreamtypes());
        return values;
    }

}