package com.potato.chips.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.potato.chips.app.MainApplication;
import com.potato.library.util.L;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 各种phone信息接口 例如 imei 手机型号，系统 ，分辨率，应用包及包名
 *
 * @author xugang
 */
public class PhoneUtils {
    private static final String TAG = "PhoneUtils";

    public static void goBackOperate(Context context) {

        ((Activity) context).finish();

//	    if (isApplicationInBackground(context)) {
            /*ComponentName comp = new ComponentName(MainApplication.context.getPackageName(),
                MainApplication.context.getPackageName() + ".GuideActivity");
			Intent intent = new Intent().setComponent(comp);
			intent.setAction("android.intent.action.MAIN");
			intent.addCategory("android.intent.category.LAUNCHER");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/


//		    ComponentName comp = new ComponentName(MainApplication.context.getPackageName(),
//				    MainApplication.context.getPackageName() + ".MainActivityNew");
//		    Intent intent = new Intent().setComponent(comp);
//		    intent.setAction("com.mobile17173.game.action.SHORTCUT");
//		    intent.addCategory("android.intent.category.DEFAULT");
//		    context.startActivity(intent);


        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);
            // 通过getPackageManager()的queryIntentActivities方法遍历  
            List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                // packagename = 参数packname    
                String packageName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]  
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent  
                ComponentName comp = new ComponentName(packageName, className);
                Intent intent = new Intent().setComponent(comp);
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); //FLAG_ALWAYS_RETAIN_TASK_STATE
                context.startActivity(intent);
            }
        } catch (NameNotFoundException e) {

        }
//	    }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前程序渠道
     *
     * @param context 上下文
     * @return 当前程序渠道
     */

    public static String get_Channel_ID(Context context) {
        String code = getMetaData(context, "TD_CHANNEL_ID");
        if (!TextUtils.isEmpty(code)) {
            return code;
        }
        return "A0010001001";
    }

    public static String get_PAY_Channel_ID(Context context) {
        String code = getMetaData(context, "PAY_CHANNEL_ID");
        if (!TextUtils.isEmpty(code)) {
            return code;
        }
        return "coc_android_show";
    }

    private static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(
                            context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前手机分辨率
     *
     * @param context 上下文
     * @return 手机分辨率
     */
    public static String getPhoneDisplayMetrics(Context context) {
        String sPoneDisplayMetrics = null;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager w = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        w.getDefaultDisplay().getMetrics(dm);
        sPoneDisplayMetrics = dm.widthPixels + " * " + dm.heightPixels;
        L.d(sPoneDisplayMetrics);
        return sPoneDisplayMetrics;
    }

    /**
     * 获取手机的相关信息 wangqing 2013-6-25 String 返回设备的信息
     *
     * @param context
     * @return
     */
    public static String getDeviceUA(Context context) {
        // UA号
        String UAInfo = "android_17173";
        try {
            UAInfo = "17173_" + getCurrentAppVersionName(context) + "_"
                    + get_Channel_ID(context) + "_"
                    + MainApplication.IMEI //getDeviceTokenToMD5(context)
                    + "(android_OS_"
                    + Build.VERSION.RELEASE + ";" + Build.MANUFACTURER + "_"
                    + Build.MODEL + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return "17173_" + getCurrentAppVersionName(context) + "_"
                    + get_Channel_ID(context) + "_"
                    + MainApplication.IMEI + "(android;unknown)";
        }

        L.d("getDeviceUA==" + UAInfo);
        return UAInfo;
    }

    /**
     * 获取当前程序版本名
     *
     * @param context 上下文
     * @return 当前程序版本名
     */
    public static String getCurrentAppVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            L.e(TAG, e.getMessage());
        }
        return versionName;
    }

    /**
     * 获取屏幕的宽和高的工具类 WangQing 2013-8-12 DisplayMetrics
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getAppWidthAndHeight(Context context) {
        // 这个可以用于1.5
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    /**
     * 获取手机 UID 首先是imei+phone_id 其次是是随机生成字符串
     */

    public static String getPhoneUID(Context context) {
//      String strUID = getPhoneIMEI(context);
//      if (TextUtils.isEmpty(strUID)) {
//          strUID = getPhoneWLANMac(context);
//          if (TextUtils.isEmpty(strUID)) {
//              Random ran = new Random(System.currentTimeMillis());
//              for (int i = 0; i < 4; i++) {
//                  strUID = strUID + ran.nextInt(100);
//              }
//          }
//      }   
        String strUID = ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
//        strUID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(strUID)) {
            Random ran = new Random(System.currentTimeMillis());
            for (int i = 0; i < 18; i++) {
                strUID = strUID + ran.nextInt(100);
            }
        }
        L.d("手机UID==" + strUID);
        return strUID;
    }

    public static String timeStamp2Date(String timeStamp) {
        long time = Long.valueOf(timeStamp);
        Date date = new Date(time);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String date2TimeStamp(String time) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStamp = 0;
        try {
            Date date = sdf.parse(time);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(timeStamp);
    }

    public static boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (pid == appProcess.pid) {
                L.i("MainApplication", "onCreate(),pid=" + pid
                        + ",processName=" + appProcess.processName
                        + ",PackageName=" + context.getPackageName());
                if (appProcess.processName.equals(context.getPackageName())) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static String  getIMEI(Context context) {
        String  IMEI = SPUtils.read(context,
                SPUtils.SP_COM_CYOU_ACTIVATE_CODE_UID,
                SPUtils.PREF_KEY_ACTIVATE_CODE_UID, "");

        if (TextUtils.isEmpty(IMEI)) {
            IMEI = PhoneUtils.getPhoneUID(context);
            L.d("MainApplication_phone_uid==" + IMEI);
        }
        return IMEI;
    }
}