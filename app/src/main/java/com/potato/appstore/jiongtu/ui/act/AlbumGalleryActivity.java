package com.potato.appstore.jiongtu.ui.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.potato.appstore.R;
import com.potato.appstore.jiongtu.data.bean.JiongtuPhoto;
import com.potato.appstore.jiongtu.ui.adapter.JiongTuAlbumGalleryAdapter;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ShareUtil;
import com.potato.chips.util.UIUtils;
import com.potato.chips.views.MyGallery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 囧图Gally模式
 *
 * @author zhaobingfeng
 */
public class AlbumGalleryActivity extends BaseActivity {


    /**
     * extrars
     */
//    public final static String EXTRARS_KEY_PIC = "EXTRARS_KEY_PIC";
    public final static String EX_JSONARRAY = "EX_JSONARRAY";
    public final static String EX_INDEX = "EX_INDEX";
    /** views */
    /** adapters */
    /** data */
    /** logic */


    /**
     * 图片在SD卡上的保存路径
     */
    private static final String PIC_SAVE_DIRECTORY = "rrbang/jiong_pic/";
    /**
     * 图片保存文件后缀
     */
    private static final String JPEG_FILE_EXTEN = ".jpg";
    private static final String GIF_FILE_EXTEN = ".gif";
    private static final int MSG_SAVE_SUCC = 1;
    private static final int MSG_SAVE_FAIL = 2;
    private static final int MSG_SAVE_EXISTS = 3;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_SAVE_SUCC:
                    toast(getResources().getString(R.string.pic_save_succ_toast,
                            msg.obj.toString()));
                    break;
                case MSG_SAVE_FAIL:
                    toast(R.string.pic_save_fail);
                    break;
                case MSG_SAVE_EXISTS:
                    toast(R.string.pic_has_saved_toast);
                    break;
            }
        }
    };
    public MyGallery mGallery;
    private TextView mPages;
    private List<JiongtuPhoto> mList = new ArrayList<JiongtuPhoto>();
    private ArrayList<String> urls;
    private JiongTuAlbumGalleryAdapter mAdapter;
    private GestureDetector gestureDetector;
    private boolean first = true;
    private TextView mTotalPages;
    /**
     * 当前图片
     */
    private JiongtuPhoto currentPhoto = null;
    private String currentPicUrl = null;
    private int cp = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
        mContext = this;
        setContentView();
        initViews();
        initData();
    }

    void setContentView() {
        setContentView(R.layout.activity_jiongtu_album_gallery);
    }

    void initViews() {
        ImageView mReturnBtn = (ImageView) findViewById(R.id.album_gallery_back);
        ImageView mDownload = findViews(R.id.album_gallery_download);
        mReturnBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDownload.setOnClickListener(this);
        mPages = (TextView) findViewById(R.id.tv_currentIndex);// 页码
        mTotalPages = (TextView) findViewById(R.id.tv_totalPages);
        mGallery = (MyGallery) findViewById(R.id.album_gallery);// Gallery
        mGallery.setOnItemSelectedListener(new OnGalleryItemSelectedListener());
    }

    protected void initData() {
        gestureDetector = new GestureDetector(new MyGestureDetector());
        mGallery.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent e) {
                return gestureDetector.onTouchEvent(e);
            }
        });
    }

    /**
     * 刷新适配器数据
     */
    private void refresh() {
        if (mAdapter == null) {
            mAdapter = new JiongTuAlbumGalleryAdapter(this);
            mAdapter.setDataList(mList);
            mGallery.setAdapter(mAdapter);
        } else {
            mAdapter.setDataList(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (first) {
            first = false;
            Intent intent = getIntent();
            mList = (List<JiongtuPhoto>) intent.getSerializableExtra(EX_JSONARRAY);
//            mList =  (JSONArray)intent.getSerializableExtra(EX_JSONARRAY);
            if (mList == null) {
                urls = (ArrayList<String>) intent.getSerializableExtra("urls");
                if (urls != null) {
                    mTotalPages.setText(urls.size() + "");
                }
            } else {
                mTotalPages.setText(mList.size() + "");
            }
            int modelIndex = getIntent().getIntExtra(EX_INDEX, 0);
            refresh();
            mGallery.setSelection(modelIndex); // 初始化选中的图片
            mPages.setText((modelIndex + 1) + "");
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.album_gallery_download:
               String url = currentPhoto.getBigUrl();

                ShareUtil.shareImage(v.getContext(), url);

                break;
        }
    }


    private class OnGalleryItemSelectedListener implements
            OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            cp = position;
            mPages.setText((position + 1) + "");
            if (mList != null) {
                currentPhoto = mList.get(position);
            } else if (urls != null) {
                currentPicUrl = urls.get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // 手势判断滑动
    public class MyGestureDetector extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (cp == 0 && Math.abs(distanceY) < 5 && distanceX < -20) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                return true;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // // if (!scale) {
            // CommonUtils.showToast(mActivity, "进入缩放模式", Toast.LENGTH_SHORT);
            // // mGallery.setIntercept(false);
            // } else {
            // // mAdapter.touchView.initView();
            // // mGallery.setIntercept(true);
            // }
            return super.onDoubleTap(e);
        }
    }


    public static void startPage(Context from, String jsonarray) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable(EX_JSONARRAY, jsonarray);
        bundle.putString(EX_JSONARRAY, jsonarray);
        PageCtrl.start(from, AlbumGalleryActivity.class, false, null, bundle);
//       PageCtrl.startForResult(from, OrderObjectActivity.class, false, null, bundle,);
    }

    public static void startPage(Context from, List<JiongtuPhoto> mData, int index) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable(EX_JSONARRAY, jsonarray);
        bundle.putSerializable(EX_JSONARRAY, (Serializable) mData);
        bundle.putInt(EX_INDEX, index);

        PageCtrl.start(from, AlbumGalleryActivity.class, false, null, bundle);
//       PageCtrl.startForResult(from, OrderObjectActivity.class, false, null, bundle,);
    }


    public void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast提示,默认时长为LENGTH_LONG
     *
     * @param resId 要toast的文字资源ID
     */
    public void toast(int resId) {
        toast(getResources().getString(resId));
    }

    /**
     * Toast提示
     *
     * @param msg      要Toast的文字内容
     * @param duration 显示时间,Toast.LENGTH_SHORT/Toast.LENGTH_LONG
     */
    public void toast(String msg, int duration) {
        UIUtils.toast(mContext, msg, duration);
    }

    protected <T extends View> T findViews(int ViewId) {
        return (T) findViewById(ViewId);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 统计信息，到umeng
//        MobclickAgent.onEvent(mContext, "查看活动多图");
    }
}
