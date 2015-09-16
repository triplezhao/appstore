package com.potato.chips.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ztw on 2015/8/3.
 */
public class ShareUtil {
    public static void shareImage(Context context,File file){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));  //传输图片或者文件 采用流的方式
        intent.putExtra(Intent.EXTRA_TEXT, "分享分享微博");   //附带的说明信息
        intent.putExtra(Intent.EXTRA_SUBJECT, "标题");
        intent.setType("image/*");   //分享图片
        context.startActivity(Intent.createChooser(intent, "分享"));
    }
    public static void shareImage(Context context,String url){
        File file = getShareImageFile(context,url);
        if(file==null||!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));  //传输图片或者文件 采用流的方式
        intent.putExtra(Intent.EXTRA_TEXT, "分享分享微博");   //附带的说明信息
        intent.putExtra(Intent.EXTRA_SUBJECT, "标题");
        intent.setType("image/*");   //分享图片
        context.startActivity(Intent.createChooser(intent, "分享"));
    }


    /*
 * Java文件操作 获取文件扩展名
 *
 */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile)
    {
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex)
        {
            return -1;
        }
    }

    public static File getShareImageFile(Context context,String url){
        File file = ImageLoader.getInstance().getDiskCache().get(url);
        if(file==null||!file.exists()) return null;
        String newfilename = getFileNameNoEx(file.getName())+"."+getExtensionName(url);
        File shareDir= StorageUtils.getIndividualCacheDirectory(context,"share");
        File newfile = null;
        if(shareDir.exists()){
            newfile =  new File(shareDir, newfilename);
            CopySdcardFile(file.getAbsolutePath(),newfile.getAbsolutePath());
        }
        return newfile;
    }
}
