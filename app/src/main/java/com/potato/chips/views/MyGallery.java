package com.potato.chips.views;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView.ScaleType;

import com.potato.library.util.L;

public class MyGallery extends Gallery {
    private static final String TAG = "MyGallery";
    private GestureDetector gestureScanner;
    private MyImageView imageView;
    private int mGalleryWidth = 480;
    private int mGalleryHeight = 800;
    float baseValue;
    float originalScale;


    public MyGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureScanner = new GestureDetector(new MySimpleGesture());// 处理双击事件
        this.setOnTouchListener(new MyTouchListener());//处理双指缩放事件
    }

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureScanner = new GestureDetector(new MySimpleGesture());// 处理双击事件
        this.setOnTouchListener(new MyTouchListener());//处理双指缩放事件
    }
    public MyGallery(Context context) {
        super(context);
        gestureScanner = new GestureDetector(new MySimpleGesture());// 处理双击事件
        this.setOnTouchListener(new MyTouchListener());//处理双指缩放事件
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        View view = MyGallery.this.getSelectedView();
        if (view instanceof MyImageView) {
            imageView = (MyImageView) view;
            float v[] = new float[9];
            Matrix m = imageView.getImageMatrix();
            m.getValues(v);
            // 图片实时的上下左右坐标
            float left, right, top, bottom;
            // 图片的实时宽，高
            float width, height;
            width = imageView.getScale() * imageView.getImageWidth();
            height = imageView.getScale() * imageView.getImageHeight();
            // 以下逻辑为移动图片和滑动gallery换屏的逻辑
            if ((int) width <= mGalleryWidth && (int) height <= mGalleryHeight)// 如果图片当前大小<屏幕大小，直接处理滑屏事件
            {
                super.onScroll(e1, e2, distanceX, distanceY);
            } else {
                left = v[Matrix.MTRANS_X];
                top = v[Matrix.MTRANS_Y];
                right = left + width;
                bottom = top + height;
                Rect r = new Rect();
                imageView.getGlobalVisibleRect(r);
                if (Math.abs(distanceX) > Math.abs(distanceY) && distanceX > 0) {// 向左滑动
                    if (r.left > 0) {// 判断当前ImageView是否显示完全
                        super.onScroll(e1, e2, distanceX, distanceY);
                    } else if (right < mGalleryWidth) {
                        super.onScroll(e1, e2, distanceX, distanceY);
                    } else {
                        imageView.postTranslate(-distanceX, 0);
                    }
                } else if (Math.abs(distanceX) > Math.abs(distanceY)
                        && distanceX < 0) {// 向右滑动
                    if (r.right < mGalleryWidth) {
                        super.onScroll(e1, e2, distanceX, distanceY);
                    } else if (left > 0) {
                        super.onScroll(e1, e2, distanceX, distanceY);
                    } else {
                        imageView.postTranslate(-distanceX, 0);
                    }
                } else if (Math.abs(distanceX) < Math.abs(distanceY)
                        && distanceY < 0) {// 向下滑动
                    if (top > 0) {// 图片顶部未超出屏幕，不允许图片向下移动
                        // super.onScroll(e1, e2, distanceX, distanceY);
                    } else {
                        imageView.postTranslate(-distanceX, -distanceY);
                    }
                } else if (Math.abs(distanceX) < Math.abs(distanceY)
                        && distanceY > 0) {// 向上滑动
                    if (bottom < mGalleryHeight) {// 图片底部未超出屏幕，不允许图片向上移动
                        // super.onScroll(e1, e2, distanceX, distanceY);
                    } else {
                        imageView.postTranslate(-distanceX, -distanceY);
                    }
                }
            }
        } else {
            super.onScroll(e1, e2, distanceX, distanceY);
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        int keyCode;
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureScanner.onTouchEvent(event);
        View view = MyGallery.this.getSelectedView();
        if (view == null) {
            return super.onTouchEvent(event);
        }
        if (view instanceof MyImageView) {
            imageView = (MyImageView) view;
        } else {
            return super.onTouchEvent(event);
        }
        imageView.setScaleType(ScaleType.MATRIX);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            baseValue = 0;
            originalScale = imageView.getScale();
            break;
        case MotionEvent.ACTION_MOVE:
            if (event.getPointerCount() == 2) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
                // System.out.println("value:" + value);
                if (baseValue == 0) {
                    baseValue = value;
                } else {
                    float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
                    // scale the image
                    // L.e("scale:"+scale);
                    imageView.zoomTo(originalScale * scale, x + event.getX(1),
                            y + event.getY(1));

                }
            }
            break;
        case MotionEvent.ACTION_UP:
            // 判断上下边界是否越界

            if (view instanceof MyImageView) {
                imageView = (MyImageView) view;
                // float width = imageView.getScale() *
                // imageView.getImageWidth();
                float height = imageView.getScale()
                        * imageView.getImageHeight();
                if ((int) height <= mGalleryHeight) {// 如果图片当前大小<屏幕大小，判断边界
                    break;
                }
                float v[] = new float[9];
                Matrix m = imageView.getImageMatrix();
                m.getValues(v);
                float top = v[Matrix.MTRANS_Y];
                float bottom = top + height;
                if (top > 0) {
                    imageView.postTranslateDur(-top, 200f);
                }
                // Log.i("manga", "bottom:" + bottom);
                if (bottom < mGalleryHeight) {
                    imageView.postTranslateDur(mGalleryHeight - bottom, 200f);
                }
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    private class MyTouchListener implements OnTouchListener {
        float baseValue;
        float originalScale;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            View view = MyGallery.this.getSelectedView();
            if (view instanceof MyImageView) {
                L.i(TAG,"onTouch");
                imageView = (MyImageView) view;
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    baseValue = 0;
                    originalScale = imageView.getScale();
                } else if (action == MotionEvent.ACTION_MOVE) {
                    if (event.getPointerCount() == 2) {
                        float x = event.getX(0) - event.getX(1);
                        float y = event.getY(0) - event.getY(1);
                        float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
                        // System.out.println("value:" + value);
                        if (baseValue == 0) {
                            baseValue = value;
                        } else {
                            float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
                            // scale the image
                            imageView.zoomTo(originalScale * scale,
                                    x + event.getX(1), y + event.getY(1));
                        }
                    }
                }
            }
            return false;
        }

    }

    private class MySimpleGesture extends SimpleOnGestureListener { // 按两下的第二下Touch
                                                                    // down时触发
        public boolean onDoubleTap(MotionEvent e) {
            View view = MyGallery.this.getSelectedView();
            if (view instanceof MyImageView) {
                imageView = (MyImageView) view;
                if (imageView.getScale() > imageView.getScaleRate()) {
                    imageView.zoomTo(imageView.getScaleRate(),
                            mGalleryWidth / 2f, mGalleryHeight / 2f, 200f);
                    // imageView.layoutToCenter();
                } else {
                    imageView.zoomTo(imageView.getScaleRate(),
                            mGalleryWidth / 2f, mGalleryHeight / 2f, 200f);
                }
            } else {

            }
            // return super.onDoubleTap(e);
            return true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGalleryHeight = h;
        mGalleryWidth = w;
//        L.e(TAG, "onSizeChanged: w=" + w + ",h=" + h + ",oldw=" + oldw+ ",oldh=" + oldh);
    }
}
