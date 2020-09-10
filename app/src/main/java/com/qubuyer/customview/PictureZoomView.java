package com.qubuyer.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.customview.picture.PhotoView;
import com.qubuyer.customview.picture.PhotoViewAttacher;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @date 创建时间:2019/4/19
 * @author Susong
 * @description 显示图片大图View
 & @version
 */
public class PictureZoomView extends BaseActivity {
    private static final String INTENT_KEY_INDEX = "index";
    private static final String INTENT_KEY_TYPE = "type";
    private static final String INTENT_KEY_LOCAL = "local";
    private static final String INTENT_KEY_URL = "url";
    private static final String INTENT_KEY_BITMAP = "bitmap";
    // private static final String INTENT_KEY_CALLBACK = "callback";

    private static final int TYPE_LOCAL = 0;
    private static final int TYPE_URL = 1;
    private static final int TYPE_BITMAP = 2;

    private ViewPager mViewPager;
    private SamplePagerAdapter mPictureAdapter;

    //当前显示的图片url
    private String mShowImageUrl;
    private static int mCode = 100;


    /**
     * 单张网络地址
     *
     * @param context
     * @param url
     * @author jiangtianming
     */
    public static void actionStartUrl(Context context, String url) {
        Intent intent = new Intent(context, PictureZoomView.class);
        List<String> urls = new ArrayList<>();
        urls.add(url == null ? "" : url);
        intent.putExtra(INTENT_KEY_URL, (Serializable) urls);
        intent.putExtra(INTENT_KEY_LOCAL, (Serializable) urls);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_URL);
        context.startActivity(intent);
    }

    /**
     * 多张网络地址
     *
     * @param context
     * @param urls
     * @param index   要显示当前第几张 0是第一张
     * @author jiangtianming
     */
    public static void actionStartUrl(Context context, List<String> urls, int index) {
        Intent intent = new Intent(context, PictureZoomView.class);
        intent.putExtra(INTENT_KEY_URL, (Serializable) urls);
        intent.putExtra(INTENT_KEY_INDEX, index);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_URL);
        context.startActivity(intent);
    }

    /**
     * 多张网络地址
     *
     * @param activity
     * @param urls
     * @param index    要显示当前第几张 0是第一张
     * @author Zhuhh
     */
    public static void actionStartUrl(Activity activity, List<String> urls, int index) {
        Intent intent = new Intent(activity, PictureZoomView.class);
        intent.putExtra(INTENT_KEY_URL, (Serializable) urls);
        intent.putExtra(INTENT_KEY_INDEX, index);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_URL);
        activity.startActivityForResult(intent, mCode);
    }


    /**
     * 单张本地地址
     *
     * @param context
     * @param localPath
     * @author jiangtianming
     */
    public static void actionStartFile(Context context, String localPath) {
        Intent intent = new Intent(context, PictureZoomView.class);
        List<String> localList = new ArrayList<>();
        localList.add(localPath == null ? "" : localPath);
        intent.putExtra(INTENT_KEY_LOCAL, (Serializable) localList);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_LOCAL);
        context.startActivity(intent);
    }

    /**
     * 多张本地地址
     *
     * @param context
     * @param localPathList
     * @param index         要显示当前第几张 0是第一张
     * @author jiangtianming
     */
    public static void actionStartFile(Context context, List<String> localPathList, int index) {
        Intent intent = new Intent(context, PictureZoomView.class);
        intent.putExtra(INTENT_KEY_LOCAL, (Serializable) localPathList);
        intent.putExtra(INTENT_KEY_INDEX, index);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_LOCAL);
        context.startActivity(intent);
    }

    /**
     * 单个bitmap显示 调用此构造需要注意外面的bitmap需要自行回收,避免造成内存溢出
     *
     * @param context
     * @param bitmap
     * @author jiangtianming
     */
    public static void actionStartBitmap(Context context, Bitmap bitmap) {
        Intent intent = new Intent(context, PictureZoomView.class);
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        if (bitmap != null)
            bitmaps.add(bitmap);
        intent.putExtra(INTENT_KEY_BITMAP, (Serializable) bitmaps);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_BITMAP);
        context.startActivity(intent);
    }

    /**
     * 多个bitmap显示 调用此构造需要注意外面的bitmap需要自行回收,避免造成内存溢出
     *
     * @param context
     * @param urls
     * @param index   要显示当前第几张 0是第一张
     * @author jiangtianming
     */
    public static void actionStartBitmap(Context context, List<String> urls, int index) {
        Intent intent = new Intent(context, PictureZoomView.class);
        intent.putExtra(INTENT_KEY_BITMAP, (Serializable) urls);
        intent.putExtra(INTENT_KEY_INDEX, index);
        intent.putExtra(INTENT_KEY_TYPE, TYPE_BITMAP);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initView();
    }

    protected void initView() {
        int defaultShowIndex = getIntent().getIntExtra(INTENT_KEY_INDEX, 0);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_picture);
        mPictureAdapter = new SamplePagerAdapter();
        mViewPager.setAdapter(mPictureAdapter);
        if (mPictureAdapter.getCount() - 1 >= defaultShowIndex) {
            mViewPager.setCurrentItem(defaultShowIndex);
        }
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 图片展示适配器
     *
     * @author jiangtianming
     * @date 2014-12-25 下午9:09:57
     * @Description:
     */
    class SamplePagerAdapter extends PagerAdapter implements View.OnClickListener {
        private int type;
        private List<String> locals;
        private List<String> urls;
        private List<Bitmap> bitmaps;

        public SamplePagerAdapter() {
            type = getIntent().getIntExtra(INTENT_KEY_TYPE, TYPE_URL);
            locals = (List<String>) getIntent().getSerializableExtra(INTENT_KEY_LOCAL);
            urls = (List<String>) getIntent().getSerializableExtra(INTENT_KEY_URL);
            bitmaps = (List<Bitmap>) getIntent().getSerializableExtra(INTENT_KEY_BITMAP);
        }

        @Override
        public int getCount() {
            switch (type) {
                case TYPE_LOCAL:
                    return locals == null ? 0 : locals.size();
                case TYPE_BITMAP:
                    return bitmaps == null ? 0 : bitmaps.size();
                case TYPE_URL:
                    return urls == null ? 0 : urls.size();
                default:
                    return 0;
            }
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            // 加载view开始
            View viewPictureItem = LayoutInflater.from(PictureZoomView.this).inflate(R.layout.show_picture_item, null);
            ProgressBar bar = viewPictureItem.findViewById(R.id.progressbar_show_picture);
            PhotoView photoView = viewPictureItem.findViewById(R.id.photoview_show_picture);
            FrameLayout.LayoutParams barLayoutParam = new FrameLayout.LayoutParams(50, 50);
            barLayoutParam.gravity = Gravity.CENTER;
            bar.setLayoutParams(barLayoutParam);
            container.addView(viewPictureItem, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnClickListener(this);
            // 加载view结束
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            // 加载图片
            loadPhoto(photoView, bar, position);
            return viewPictureItem;
        }

        /**
         * 显示图片
         *
         * @param photoView
         * @param bar
         * @param position
         */
        public void loadPhoto(PhotoView photoView, final ProgressBar bar, int position) {
            switch (type) {
                case TYPE_LOCAL:
                    final File file = new File(locals.get(position));
                    if (file.exists()) {
                        Glide.with(PictureZoomView.this).load(file).into(photoView);
                    } else {
                        Glide.with(PictureZoomView.this).load(locals.get(position)).into(photoView);
                    }
                    break;
                case TYPE_URL:
                    Glide.with(PictureZoomView.this).load(urls.get(position)).into(photoView);
                    break;
                case TYPE_BITMAP:
                    photoView.setImageBitmap(bitmaps.get(position));
                    break;
            }
            //图片加载完成，关闭进度条
            bar.setVisibility(View.GONE);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewGroup root = (ViewGroup) object;
            container.removeView(root);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleBitmap();
    }

    @Override
    protected int getContentView() {
        return R.layout.show_picture;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    /**
     * 释放图片资源
     *
     * @author jiangtianming
     */
    private void recycleBitmap() {
        List<Bitmap> bitmaps = (List<Bitmap>) getIntent().getSerializableExtra(INTENT_KEY_BITMAP);
        for (int i = 0; bitmaps != null && i < bitmaps.size(); i++) {
            Bitmap b = bitmaps.get(i);
            if (b != null && !b.isRecycled()) {
                b.recycle();
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}