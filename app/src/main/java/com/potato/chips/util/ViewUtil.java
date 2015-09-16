package com.potato.chips.util;

import android.os.Build;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by ztw on 2015/8/7.
 */
public class ViewUtil {


    public static void changeViewLayerType(View v,int layertype) {
//        public static final int LAYER_TYPE_HARDWARE = 2;
//        public static final int LAYER_TYPE_NONE = 0;
//        public static final int LAYER_TYPE_SOFTWARE = 1;
        if (Build.VERSION.SDK_INT >= 11) {
            Class<?> classType = v.getClass();
            try {
                Method method = classType
                        .getMethod("setLayerType", new Class[] { int.class,
                                android.graphics.Paint.class });
                method.invoke(v, new Object[] { layertype, null });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
