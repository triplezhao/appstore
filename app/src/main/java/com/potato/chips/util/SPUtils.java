package com.potato.chips.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Use this class to read and write the preferences
 *
 * @author guangwei
 */
public class SPUtils {
    // The name of the preference
    public static final String SP_NAME_DEFAULT = "sp_name_default";
    public static final String SP_COM_CYOU_ACTIVATE_CODE_UID = "com_cyou_activate_code_uid";

    // The key of the preference
    public static final String PREF_KEY_ACTIVATE_CODE_UID = "activate_code_uid";

    public static void write(Context ctx, String spName, String key,
                             String value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static void write(Context ctx, String spName, String key, int value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static void write(Context ctx, String spName, String key, long value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static void write(Context ctx, String spName, String key,
                             boolean value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean read(Context ctx, String spName, String key,
                               boolean defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static int read(Context ctx, String spName, String key, int defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    public static String read(Context ctx, String spName, String key,
                              String defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    public static long read(Context ctx, String spName, String key,
                            long defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);
    }

    public static void clear(Context ctx, String spName) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void clear(Context ctx, String spName, String key) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }

    public static SharedPreferences getSharedPreferences(Context ctx,
                                                         String spName) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     *         name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     *         name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
