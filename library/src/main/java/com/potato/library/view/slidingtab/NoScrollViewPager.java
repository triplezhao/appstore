package com.potato.library.view.slidingtab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        // TODO Auto-generated method stub
//        return false;
//    }

//    @Override
//    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        // TODO Auto-generated method stub
////        if(v != this && v instanceof ViewPager) {
////          int currentItem = ((ViewPager) v).getCurrentItem();
////          int countItem = ((ViewPager) v).getAdapter().getCount();
////          if((currentItem==(countItem-1) && dx<0) || (currentItem==0 && dx>0)){
////              return false;
////          }
////          return true;
////      }
////      return super.canScroll(v, checkV, dx, x, y);
//        return true;
//    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        
        return false;
    }

}
