package com.potato.demo.youku.data.db;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.potato.chips.base.BaseProvider;

/**
 * create by freemaker
 */
public class YKVideoProvider extends BaseProvider {
    public static final String AUTHORITY = "com.mobile17173.game.YKVideoProvider";
    public static final String TABLE_PATH = "YKVideo";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY), TABLE_PATH);
    
    public static class Columns implements BaseColumns{
			public static final String comment_count = "comment_count";
			public static final String link = "link";
			public static final String state = "state";
			public static final String operation_limit = "operation_limit";
			public static final String isinteract = "isinteract";
			public static final String id = "id";
			public static final String down_count = "down_count";
			public static final String title = "title";
			public static final String duration = "duration";
			public static final String category = "category";
			public static final String thumbnail = "thumbnail";
			public static final String up_count = "up_count";
			public static final String favorite_count = "favorite_count";
			public static final String public_type = "public_type";
			public static final String view_count = "view_count";
			public static final String published = "published";
			public static final String streamtypes = "streamtypes";
    }
    
    /**
     * Use this static method to create the table
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PATH + "(" + Columns._ID + " integer primary key autoincrement, "
	                + Columns.comment_count + " text, "
	                + Columns.link + " text, "
	                + Columns.state + " text, "
	                + Columns.operation_limit + " text, "
	                + Columns.isinteract + " text, "
	                + Columns.id + " text, "
	                + Columns.down_count + " text, "
	                + Columns.title + " text, "
	                + Columns.duration + " text, "
	                + Columns.category + " text, "
	                + Columns.thumbnail + " text, "
	                + Columns.up_count + " text, "
	                + Columns.favorite_count + " text, "
	                + Columns.public_type + " text, "
	                + Columns.view_count + " text, "
	                + Columns.published + " text, "
	                + Columns.streamtypes + " text "
                + ");");
    }
    
    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURLMatcher.addURI(AUTHORITY, TABLE_PATH, BaseProvider.ITEMS);
        sURLMatcher.addURI(AUTHORITY, TABLE_PATH + "/#", BaseProvider.ITEMS_ID);
    }
    
    @Override
    public String getAuthority() {
        return AUTHORITY;
    }

    @Override
    public String getTablePath() {
        return TABLE_PATH;
    }

    @Override
    public UriMatcher getUriMatcher() {
        return sURLMatcher;
    }
    
     @Override
    public Uri getContentUri() {
        return CONTENT_URI;
    }
    
}

