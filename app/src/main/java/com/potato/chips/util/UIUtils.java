package com.potato.chips.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.potato.demo.R;
import com.potato.chips.app.MainApplication;

import org.apache.http.client.HttpResponseException;

import java.io.File;
import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import java.net.SocketException;


/**
 * 界面有关工具类 Toast 各种Dialog
 *
 * @author zhaobingfeng
 */
public class UIUtils {
    public static final int DIALOG_PROMPT = 0;
    public static final int DIALOG_NO_FEEDBACK = 1;
    public static final int DIALOG_FEEDBACK = 2;
    public static final int DIALOG_PROMPT_FEEDBACK = 3;

    private static Toast toast;

    /**
     * 弹出Toast
     *
     * @param context 上下文
     * @param msg     消息内容
     */
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出Toast
     *
     * @param context 上下文
     * @param resId   字符串资源ID
     */
    public static void toast(Context context, int resId) {
        toast(context, context.getString(resId));
    }

    /**
     * 在子线程中Toast消息
     *
     * @param context 上下文
     * @param resId   字符串资源
     */
    public static void toastAsync(final Context context, final int resId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                UIUtils.toast(context, resId);
            }
        });
    }

    public static void toastAsync(final Context context, final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                UIUtils.toast(context, text);
            }
        });
    }

    /**
     * 弹出Toast
     *
     * @param context  上下文
     * @param msg      消息内容
     * @param duration 时长
     */
    public static void toast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void toastAsync(final Context context, final Throwable error) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                UIUtils.toast(context, error);
            }
        });
    }

    public static void toast(Context context, Throwable error) {
        if (context == null)
            return;
        if (toast == null) {
            toast = Toast.makeText(context, R.string.potato_net_timeout,
                    Toast.LENGTH_SHORT);
        }

        // 304 表示该设备已经激活过，可认为激活成功，继续请求数据
        if (error instanceof HttpResponseException) {
            int code = ((HttpResponseException) error).getStatusCode();
            switch (code) {
                case 502:
                case 504:
                case 408:
                case 0:
                    toast.setText(R.string.potato_net_timeout);
                    break;
                case 9999:
                    toast.setText(R.string.potato_no_net);
                    break;
                default:
                    // toast.setText(""+code);

                    int errorcode = code / 100;
                    if (errorcode == 4 || errorcode == 5) {
                        toast.setText(R.string.potato_data_exception);
                    }

                    break;
            }
        } else if (error instanceof InterruptedIOException) {
            toast.setText(R.string.potato_net_timeout);
        } else if (error instanceof SocketException) {
            toast.setText(R.string.potato_net_timeout);
        } else {
            // toast.setText("error=="+error);
        }
        toast.show();
    }

    /**
     * 设置View的硬件加速为关闭状态 默认在API 11上可直接使用API设置，此处使用反射调用
     *
     * @param v 要修改的View
     */
    public static void changeViewLayerType(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            Class<?> classType = v.getClass();
            try {
                Method method = classType
                        .getMethod("setLayerType", new Class[]{int.class,
                                android.graphics.Paint.class});
                method.invoke(v, new Object[]{1, null});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 顶部通知栏弹出的方式
     * 
     * @param context 上下文 
     * @param title 标题
     * @param downloadState 下载的状态，可以为空
     * @param text 显示的内容
     * @param show_icon 提示的图片，在顶部弹出
     * @param notifyId 这个通知的惟一标识的id （每个应用应该是唯一的）
     * @param totalSize 文件总大小
     * @param currentSize 当前下载的进度数
     * @param file 文件路径，可以不传递
     * @param isForbidCancel 是否可以 禁止 取消顶部通知栏的通知
     */
    public static void showNotificationAppDownload(Context context,
            String title, String downloadState, String text, int show_icon,
            int notifyId, long totalSize, long currentSize, File file,
            boolean isForbidCancel) {
        context = MainApplication.context;
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification的滚动提示
        String tickerText = text;
        int icon = show_icon;

        // 创建Notifcation
        Notification notification = new Notification(icon, tickerText, notifyId);
        if (isForbidCancel) {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        } else {
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
        }

        int precent = 0;
        if (totalSize > 0) {
            precent = (int) (currentSize * 100 / totalSize);
        }

        // 创建RemoteViews用在Notification中
        RemoteViews contentView = new RemoteViews(context.getPackageName(),R.layout.notification_view_layout);
        contentView.setTextViewText(R.id.notificationTitle, title);
        contentView.setTextViewText(R.id.downlaod_state, downloadState);
        contentView.setTextViewText(R.id.notificationPercent, precent + "%");
        contentView.setProgressBar(R.id.notificationProgress, 100, precent,
                false);
        notification.contentView = contentView;

        Intent intent = null;
        if (file != null) {
            intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        } else {
            intent = new Intent();
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        nm.notify(notifyId, notification);
    }

}
