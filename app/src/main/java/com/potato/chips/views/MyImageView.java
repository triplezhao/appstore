package com.potato.chips.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.potato.chips.app.MainApplication;
import com.potato.library.util.L;


public class MyImageView extends ImageView {

    private static final String TAG = "MyImageView";

    // This is the base transformation which is used to show the image
    // initially. The current computation for this shows the image in
    // it's entirety, letterboxing as needed. One could choose to
    // show the image as cropped instead.
    //
    // This matrix is recomputed when we go from the thumbnail image to
    // the full size image.
    protected Matrix mBaseMatrix = new Matrix();

    // This is the supplementary transformation which reflects what
    // the user has done in terms of zooming and panning.
    //
    // This matrix remains the same when we go from the thumbnail image
    // to the full size image.
    protected Matrix mSuppMatrix = new Matrix();

    // This is the final matrix which is computed as the concatentation
    // of the base matrix and the supplementary matrix.
    private final Matrix mDisplayMatrix = new Matrix();

    // Temporary buffer used for getting the values out of a matrix.
    private final float[] mMatrixValues = new float[9];

    // The current bitmap being displayed.
    // protected final RotateBitmap mBitmapDisplayed = new RotateBitmap(null);
    protected Bitmap image = null;

    int mThisWidth = -1, mThisHeight = -1;

    float mMaxZoom = 10.0f;// 最大缩放比例,默认10陪
    float mMinZoom = 0.5f;// 最小缩放比例,默认0.5倍

    private int imageWidth;// 图片的原始宽度
    private int imageHeight;// 图片的原始高度
    private int screenWidth = 480;
    private int screenHeight = 800;

    private float scaleRate;// 图片适应屏幕的缩放比例

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context) {
        super(context);
        init();
    }

    /**
     * 计算图片要适应屏幕需要缩放的比例
     */
    private void arithScaleRate() {
        float scaleWidth = screenWidth / (float) imageWidth;
        float scaleHeight = screenHeight / (float) imageHeight;
        scaleRate = Math.min(scaleWidth, scaleHeight);
    }

    public float getScaleRate() {
        return scaleRate;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    protected Handler mHandler = new Handler();

    @Override
    public void setImageDrawable(Drawable drawable) {
        setVisibility(View.INVISIBLE);
        super.setImageDrawable(drawable);
        if (drawable instanceof TransitionDrawable) {
            TransitionDrawable td = (TransitionDrawable) drawable;
            drawable = td.getDrawable(1);
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap i = ((BitmapDrawable) drawable).getBitmap();
            if (i == null) {
                return;
            }
            image = i;
            L.e(TAG, "setImageDrawable");
            isConfigurationChanged = true;
            initImage();
        }
        // zoomTo(1.0f, screenWidth / 2, screenHeight / 2, 0.1f);
        // 居中
        // layoutToCenter();
    }

    private void initImage() {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        arithScaleRate();
        // 缩放到屏幕大小
        zoomTo(scaleRate, screenWidth / 2f, screenHeight / 2f, 0.1f);
    }

    // Center as much as possible in one or both axis. Centering is
    // defined as follows: if the image is scaled down below the
    // view's dimensions then center it (literally). If the image
    // is scaled larger than the view and is translated out of view
    // then translate it back into view (i.e. eliminate black bars).
    protected void center(boolean horizontal, boolean vertical) {
        // if (mBitmapDisplayed.getBitmap() == null) {
        // return;
        // }
        if (image == null) {
            return;
        }
        int imageViewWidth = screenWidth;
        int imageViewHeight = screenHeight;
        Matrix m = getImageViewMatrix();

        // RectF rect = new RectF(0, 0, image.getWidth()*viewWidth/720,
        // image.getHeight()*viewHeight/1280);
        RectF rect = new RectF(0, 0, image.getWidth(), image.getHeight());
        // RectF rect = new RectF(0, 0, image.getWidth()
        // * MainApplication.screenWidth / screenWidth, image.getHeight()
        // * (MainApplication.screenHight - statusBarHeight)
        // / screenHeight);
        // RectF rect = new RectF(0, 0, image.getWidth(), image.getHeight());
        // RectF rect = new RectF(0, 0, imageWidth*getScale(),
        // imageHeight*getScale());
        m.mapRect(rect);

        float width = rect.width();
        float height = rect.height();

        float deltaX = 0, deltaY = 0;

        if (vertical) {

            if (height < imageViewHeight) {
                deltaY = (imageViewHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < imageViewHeight) {
                deltaY = getHeight() - rect.bottom;
            }
        }

        if (horizontal) {

            if (width < imageViewWidth) {
                deltaX = (imageViewWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < imageViewWidth) {
                deltaX = imageViewWidth - rect.right;
            }
        }
        L.e(TAG, "center: " + deltaX + "," + deltaY);
        postTranslate(deltaX, deltaY);
        setImageMatrix(getImageViewMatrix());
    }

    private void init() {
        Point p = MainApplication.getSWAndSH(getContext());
        screenWidth = p.x;
        screenHeight = p.y;
        screenHeight -= statusBarHeight;
//        setScaleType(ImageView.ScaleType.MATRIX);
        setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        L.e("MyImageView", "--" + getWidth() + "--"
                                + getHeight());
                        screenHeight = getHeight();
                        screenWidth = getWidth();
                    }
                });
    }

    public void init(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /** * 设置图片居中显示 */
    public void layoutToCenter() { // 正在显示的图片实际宽高
        float width = imageWidth * getScale();
        float height = imageHeight * getScale(); // 空白区域宽高
        float fill_width = screenWidth - width;
        float fill_height = screenHeight - height; // 需要移动的距离
        float tran_width = 0f;
        float tran_height = 0f;
        if (fill_width > 0)
            tran_width = fill_width / 2;
        if (fill_height > 0)
            tran_height = fill_height / 2;
        float[] matrix = new float[9];
        getImageViewMatrix().getValues(matrix);
        System.out.println("5:" + matrix[Matrix.MTRANS_Y]);
        if (matrix[Matrix.MTRANS_Y] != 0) {
            // setImageMatrix(getImageViewMatrix());
            return;
        }
        postTranslate(tran_width, tran_height);
    }

    protected float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(mMatrixValues);
        // mMinZoom = (screenWidth / 2f) / imageWidth;//此处注释，使用固定的最小缩放比。

        return mMatrixValues[whichValue];
    }

    // Get the scale factor out of the matrix.
    protected float getScale(Matrix matrix) {
        return getValue(matrix, Matrix.MSCALE_X);
    }

    protected float getScale() {
        return getScale(mSuppMatrix);
    }

    // Combine the base matrix and the supp matrix to make the final matrix.
    protected Matrix getImageViewMatrix() {
        // The final matrix is computed as the concatentation of the base matrix
        // and the supplementary matrix.
        mDisplayMatrix.set(mBaseMatrix);
        mDisplayMatrix.postConcat(mSuppMatrix);
        return mDisplayMatrix;
    }

    static final float SCALE_RATE = 1.25F;

    // Sets the maximum zoom, which is a scale relative to the base matrix. It
    // is calculated to show the image at 400% zoom regardless of screen or
    // image orientation. If in the future we decode the full 3 megapixel image,
    // rather than the current 1024x768, this should be changed down to 200%.
    protected float maxZoom() {
        if (image == null) {
            return 1F;
        }

        float fw = (float) image.getWidth() / (float) mThisWidth;
        float fh = (float) image.getHeight() / (float) mThisHeight;
        float max = Math.max(fw, fh) * 4;
        return max;
    }

    protected void zoomTo(float scale, float centerX, float centerY) {
        if (scale > mMaxZoom) {
            scale = mMaxZoom;
        } else if (scale < mMinZoom) {
            scale = mMinZoom;
        }
        float oldScale = getScale();
        float deltaScale = scale / oldScale;
//        float deltaScale = 1.0557184f;
        // mSuppMatrix.postScale(deltaScale, deltaScale, centerX*w0,
        // centerY*h0);
        mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
        setImageMatrix(getImageViewMatrix());
        center(true, true);
        setVisibility(View.VISIBLE);
        invalidate();
    }

    protected void zoomTo(final float scale, final float centerX,
            final float centerY, final float durationMs) {
        mHandler.post(new Runnable() {
            public void run() {
                zoomTo(scale, centerX, centerY);
            }
        });
    }

    protected void zoomTo(float scale) {
        float cx = getWidth() / 2F;
        float cy = getHeight() / 2F;
        zoomTo(scale, cx, cy);
    }

    protected void zoomToPoint(float scale, float pointX, float pointY) {
        float cx = getWidth() / 2F;
        float cy = getHeight() / 2F;
        panBy(cx - pointX, cy - pointY);
        zoomTo(scale, cx, cy);
    }

    protected void zoomIn() {
        zoomIn(SCALE_RATE);
    }

    protected void zoomOut() {
        zoomOut(SCALE_RATE);
    }

    protected void zoomIn(float rate) {
        if (getScale() >= mMaxZoom) {
            return; // Don't let the user zoom into the molecular level.
        } else if (getScale() <= mMinZoom) {
            return;
        }
        if (image == null) {
            return;
        }

        float cx = getWidth() / 2F;
        float cy = getHeight() / 2F;

        mSuppMatrix.postScale(rate, rate, cx, cy);
        setImageMatrix(getImageViewMatrix());
    }

    protected void zoomOut(float rate) {
        if (image == null) {
            return;
        }

        float cx = getWidth() / 2F;
        float cy = getHeight() / 2F;

        // Zoom out to at most 1x.
        Matrix tmp = new Matrix(mSuppMatrix);
        tmp.postScale(1F / rate, 1F / rate, cx, cy);

        if (getScale(tmp) < 1F) {
            mSuppMatrix.setScale(1F, 1F, cx, cy);
        } else {
            mSuppMatrix.postScale(1F / rate, 1F / rate, cx, cy);
        }
        setImageMatrix(getImageViewMatrix());
        center(true, true);
    }

    public void postTranslate(float dx, float dy) {
        mSuppMatrix.postTranslate(dx, dy);
        setImageMatrix(getImageViewMatrix());
    }

    float _dy = 0.0f;

    private static int statusBarHeight = 50;

    protected void postTranslateDur(final float dy, final float durationMs) {
        _dy = 0.0f;
        final float incrementPerMs = dy / durationMs;
        final long startTime = System.currentTimeMillis();
        mHandler.post(new Runnable() {
            public void run() {
                long now = System.currentTimeMillis();
                float currentMs = Math.min(durationMs, now - startTime);

                postTranslate(0, incrementPerMs * currentMs - _dy);
                _dy = incrementPerMs * currentMs;

                if (currentMs < durationMs) {
                    mHandler.post(this);
                }
            }
        });
    }

    protected void panBy(float dx, float dy) {
        postTranslate(dx, dy);
        setImageMatrix(getImageViewMatrix());
    }

    boolean isConfigurationChanged = false;

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigurationChanged = true;
        L.e(TAG, "onConfigurationChanged");
        // int t = screenHeight;
        // screenHeight = screenWidth;
        // screenWidth = t;
        // initImage();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isConfigurationChanged) {
            isConfigurationChanged = false;
            screenHeight = h;
            screenWidth = w;
            initImage();
        }
        L.e(TAG, "onSizeChanged: w=" + w + ",h=" + h + ",oldw=" + oldw
                + ",oldh=" + oldh);
    }

}