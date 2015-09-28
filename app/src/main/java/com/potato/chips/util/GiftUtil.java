package com.potato.chips.util;

import android.content.Context;


import com.potato.library.util.L;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GiftUtil {


    public static InputStream getFileIS(String filepath) {

        File file = new File(filepath);
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file), 16 * 1024);
            is.mark(16 * 1024);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return is;

    }

    public static InputStream getAssetsFileIS(Context context, String filepath) {

        InputStream is = null;
        try {
            is = context.getAssets().open(filepath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            L.i("showgift", "not found gif in assets ,goto download it");
            if(is!=null){
                try {
                    is.close();
                    is=null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(is!=null){
                try {
                    is.close();
                    is=null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        }
        return is;

    }

}
