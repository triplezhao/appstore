package com.potato.chips.base;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.potato.library.net.NetLog;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


public abstract class BaseProvider extends ContentProvider {
	protected static String packageName;
    protected SQLiteOpenHelper mOpenHelper;
    public static final int ITEMS = 1;
    public static final int ITEMS_ID = 2;
    public static final Uri CONTENT_URI = null;
    
    public abstract String getAuthority();
    public abstract String getTablePath();
    public abstract Uri getContentUri();
    public abstract UriMatcher getUriMatcher();
    
    private static final String TAG = "BaseProvider";
    
    static{
    	
		InputStream inputStream = BaseProvider.class.getClassLoader().getResourceAsStream("assets/packagename_for_provider");
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inputStream.close();
			byte[] bts = outSteam.toByteArray();
			packageName = new String(bts, "utf-8") + ".show";
		} catch (Exception e) {
			packageName = "com.cyou.mobileshow";
		} finally{
			outSteam = null;
			inputStream = null;
		}
    		
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = DatabaseHelper.getInstance(getContext());
        NetLog.d(TAG, "In onCreate method, create the provider: " + this + ", and DatabaseHelper: " + mOpenHelper);
        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        NetLog.d(TAG, "In query method, uri: " + uri + ", selection: " + selection);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        UriMatcher uriMatcher = getUriMatcher();
        String tablePath = getTablePath();
        
        int match = uriMatcher.match(uri);
        switch (match) {
        case ITEMS:
            qb.setTables(tablePath);
            break;
        case ITEMS_ID:
            qb.setTables(tablePath);
            qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
        default:
            throw new IllegalArgumentException("Unknown URL " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor result = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);

        if (result == null) {
            NetLog.d("Query failed with uri: " + uri);
        } else {
            result.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        NetLog.d(TAG, "In update method, uri: " + uri + ", selection: " + selection);
        int count = 0;
        UriMatcher uriMatcher = getUriMatcher();
        String tablePath = getTablePath();

        int match = uriMatcher.match(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match) {
        case ITEMS:
            count = db.update(tablePath, values, selection, selectionArgs);
            break;
        case ITEMS_ID:
            String idSegment = uri.getPathSegments().get(1);
            long rowId = Long.parseLong(idSegment);
            count = db.update(tablePath, values, BaseColumns._ID + "=" + rowId, null);
            break;
        default: 
            throw new IllegalArgumentException("Cannot update URL: " + uri);
        }
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        NetLog.d(TAG, "In delete method, uri: " + uri + ", selection: " + selection);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        UriMatcher uriMatcher = getUriMatcher();
        String tablePath = getTablePath();

        switch (uriMatcher.match(uri)) {
        case ITEMS:
            count = db.delete(tablePath, selection, selectionArgs);
            break;
        case ITEMS_ID:
            String idSegment = uri.getPathSegments().get(1);
            String where = null;
            if (TextUtils.isEmpty(selection)) {
                where = BaseColumns._ID + "=" + idSegment;
            } else {
                where = BaseColumns._ID + "=" + idSegment + " AND (" + selection + ")";
            }
            count = db.delete(tablePath, where, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Cannot delete from URL: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        UriMatcher uriMatcher = getUriMatcher();
        String tablePath = getTablePath();

        int match = uriMatcher.match(uri);
        switch (match) {
        case ITEMS:
            return "vnd.android.cursor.dir/" + tablePath;
        case ITEMS_ID:
            return "vnd.android.cursor.item/" + tablePath;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    
    /**
     * 对数据库使用事务处理。
     *
     * @author wangqing
     * 
     * ArrayList<ContentProviderOperation>ops = new ArrayList<ContentProviderOperation>(); 
     * ops.add(ContentProviderOperation.newDelete(Person.CONTENT_URI).build());//添加一个删除Person表的操作
     * ops.add(ContentProviderOperation.newInsert(Home.CONTENT_URI).withValues(values).build());//添加一条记录到Home表 
     * getContentResolver().applyBatch(PROVIDER.AUTHORITY,ops);//处理事务 
     * 
     * 
     */
    @Override
    public ContentProviderResult[] applyBatch(
    		ArrayList<ContentProviderOperation> operations)
    		throws OperationApplicationException {
    	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    	db.beginTransaction();
    	
    	try {
			ContentProviderResult[] results = super.applyBatch(operations);
			db.setTransactionSuccessful();
			
			return results;
		}finally {
			db.endTransaction();
		}
    
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        String tablePath = getTablePath();
        Uri contentURI = getContentUri();

        ContentValues values = null;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(tablePath, null, values);
        Uri newUrl = ContentUris.withAppendedId(contentURI, rowId);
        
        getContext().getContentResolver().notifyChange(newUrl, null);
        return newUrl;
    }

}
