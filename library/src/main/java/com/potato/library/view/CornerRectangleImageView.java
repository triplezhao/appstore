package com.potato.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cyou.model.common.R;


/**
 * 圆角CornerRectangleImageView
 *
 * @date: 2014-5-4
 * @author: ZhongYuan
 */

/**
 * @author Administrator
 */
public class CornerRectangleImageView extends ImageView {

    private final static int SAVA_FLAG = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
            | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
    /* 声明Paint对象 */
    private Paint mPaint = new Paint();
    /* 圆角大小，如果为长宽的一半，就是圆形 */
    private int mCorner = 8;
    /* 圆角颜色，保持和背景一样的颜色，以便融合到一起 */
    private int mConerColor =  R.color.potato_white;

    public CornerRectangleImageView(Context context) {
        super(context);
    }

    public CornerRectangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CornerRectangleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.app);

        mCorner = a.getInt(R.styleable.app_corner, 8);
        mConerColor = a.getResourceId(R.styleable.app_corner_color, R.color.potato_white);

        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        final int w = getWidth();
        final int h = getHeight();
        RectF r2 = new RectF();                           //RectF对象
        r2.left = 0;                                 //左边
        r2.top = 0;                                 //上边
        r2.right = w;                                   //右边
        r2.bottom = h;

        super.onDraw(canvas);

        int sc = canvas.saveLayer(null, null, SAVA_FLAG);

        //外层颜色，保持与背景颜色一致
        mPaint.setColor(getResources().getColor(mConerColor));
        canvas.drawRect(0, 0, w, h, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //内部镂空，圆角矩形。 如果圆角为长宽的一半，则就是个圆形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.TRANSPARENT);

        canvas.drawRoundRect(r2, w / mCorner, h / mCorner, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);

    }

}
