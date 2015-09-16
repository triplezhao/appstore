package com.potato.library.util;

import android.text.TextUtils;

/**
 * 日志工具�?
 */
public class L {
	/** 日志默认TAG,可在输出日志时手动指�? */
	public static final String TAG = "Log";
	/** 日志类型-Debug */
	public static final int LOG_TYPE_DEBUG = 1;
	/** 日志类型-INFO */
	public static final int LOG_TYPE_INFO = 2;
	/** 日志类型-ERROR */
	public static final int LOG_TYPE_ERROR = 4;
	/** 日志输出类型控制 */
	public static final int LOG_LEVEL = LOG_TYPE_DEBUG | LOG_TYPE_INFO
			| LOG_TYPE_ERROR;
//	private static final int LOG_LEVEL = 0;
	/**
	 * 输出Debug日志,使用默认TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void d(String msg) {
			if (!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_DEBUG) == LOG_TYPE_DEBUG) {
				android.util.Log.d(TAG, msg);
			}
	}

	/**
	 * 输出Debug日志,使用指定TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void d(String tag, String msg) {
			if (!TextUtils.isEmpty(tag)&&!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_DEBUG) == LOG_TYPE_DEBUG) {
				android.util.Log.d(tag, msg);
			}
	}

	/**
	 * 输出Info日志,使用默认TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void i(String msg) {
			if (!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_INFO) == LOG_TYPE_INFO) {
				android.util.Log.i(TAG, msg);
			}
	}

	/**
	 * 输出Debug日志,使用指定TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void i(String tag, String msg) {
			if (!TextUtils.isEmpty(tag)&&!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_INFO) == LOG_TYPE_INFO) {
				android.util.Log.i(tag, msg);
			}
	}

	/**
	 * 输出Error日志,使用默认TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void e(String msg) {
			if (!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_ERROR) == LOG_TYPE_ERROR) {
				android.util.Log.e(TAG, msg);
			}
	}

	/**
	 * 输出Error日志,使用指定TAG
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void e(String tag, String msg) {
			if (!TextUtils.isEmpty(tag)&&!TextUtils.isEmpty(msg)&&(LOG_LEVEL & LOG_TYPE_ERROR) == LOG_TYPE_ERROR) {
				android.util.Log.e(tag, msg);
			}
	}
}
