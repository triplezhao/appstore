package com.potato.chips.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.chips.util.GiftUtil;
import com.potato.chips.util.UIUtils;

import java.io.File;
import java.io.InputStream;

public class GifView extends ImageView {
    float sx = 0, sy = 0;
    
    private String gifUrl; //gif的网络地址
    private String gifSDPath;//gif的sd卡地址
    private int  gifRid;//gif的res地址
    private int imageWidth, imageHeight;
    private Movie mm;
    private long mMovieStart;
    private float viewWidth, viewHeight, x, y;

    public GifView(Context context) {
        super(context);
        UIUtils.changeViewLayerType(this);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        UIUtils.changeViewLayerType(this);
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getGifSDPath() {
        return gifSDPath;
    }

    public void setGifSDPath(String gifSDPath) {
        this.gifSDPath = gifSDPath;
        setImageDrawable(null);
    }

    public int getGifRid() {
        return gifRid;
    }

    public void setGifRid(int gifRid) {
        this.gifRid = gifRid;
        setImageDrawable(null);
    }

    public void setImageUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // L.e("动态图片点击了"+gifUrl);
        return super.onTouchEvent(event);
    }

    @SuppressLint("NewApi")
    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setScaleType(ImageView.ScaleType.FIT_CENTER);
        // L.e("setImageDrawable:gifUrl=" + gifUrl);
        if (gifUrl != null) {
            File file = ImageLoader.getInstance().getDiskCache().get(gifUrl);
            if(file!=null&&file.exists()){
                InputStream is = GiftUtil.getFileIS(file.getAbsolutePath());
                if (is != null) {
                    mm = Movie.decodeStream(is);
                    if (mm != null) {
                        imageHeight = mm.height();
                        imageWidth = mm.width();
                        adjust();
                        invalidate();
                    }
                }
            }

          /*  byte[] bs = MainApplication.fb.getBytesFromDisk(gifUrl);
            if (bs != null) {
                mm = Movie.decodeByteArray(bs, 0, bs.length);
                if (mm != null) {
                    imageHeight = mm.height();
                    imageWidth = mm.width();
                    adjust();
                    invalidate();
                }
           }*/
        }else if(gifRid!=0){
               InputStream is = getResources().openRawResource(gifRid);
                if (is != null) {
                    mm = Movie.decodeStream(is);
                    if (mm != null) {
                        imageHeight = mm.height();
                        imageWidth = mm.width();
                        adjust();
                        invalidate();
               }
            }
        }
        else if(!TextUtils.isEmpty(gifSDPath)){
            InputStream is = GiftUtil.getFileIS(gifSDPath);
            if (is != null) {
                mm = Movie.decodeStream(is);
                if (mm != null) {
                    imageHeight = mm.height();
                    imageWidth = mm.width();
                    adjust();
                    invalidate();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (imageHeight != 0 && mm != null) {
            long now = android.os.SystemClock.uptimeMillis();
           
            if (mMovieStart == 0) { // first time
                mMovieStart = now;
            }
            if (mm != null) {
                int dur = mm.duration();
                if (dur == 0) {
                    dur = 1000;
                }
                int relTime = (int) ((now - mMovieStart) % dur);
                mm.setTime(relTime);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG));
                canvas.scale(sx, sx);
                mm.draw(canvas, x, y);
                invalidate();
            }
        } else {
            super.onDraw(canvas);
        }
    }

    private void adjust() {
        if(imageWidth*imageHeight<=0){
            return;
        }
        if (sx == 0) {
            sx = viewWidth / imageWidth;
        }
        if (sy == 0) {
            sy = viewHeight / imageHeight;
        }
        sx = Math.min(sx, sy);

        x = (viewWidth - sx * imageWidth) / (2 * sx);
        y = (viewHeight - sx * imageHeight) / (2 * sx);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        adjust();
    }
}
