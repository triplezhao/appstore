package com.potato.library.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RequestCacheDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "req_cache.db";
    private static RequestCacheDBHelper sInstance;

    private RequestCacheDBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    private RequestCacheDBHelper(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    public static RequestCacheDBHelper getInstance(Context context) {
        Context cxt = context.getApplicationContext();
        if (sInstance == null)
            sInstance = new RequestCacheDBHelper(cxt);
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RequestCacheProvider.createTable(db);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
        case 1:
        }
    }

   
}
