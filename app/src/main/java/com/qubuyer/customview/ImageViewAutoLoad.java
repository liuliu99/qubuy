package com.qubuyer.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.qubuyer.R;
import com.qubuyer.utils.ImageUtil;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 自动加载网络图片的ImageView
 */
public class ImageViewAutoLoad extends AppCompatImageView {

    public ImageViewAutoLoad(Context context) {
        super(context);
        initView(context, null);
    }

    public ImageViewAutoLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ImageViewAutoLoad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        int drawableId = -1;
        int drawableCircularId = -1;
        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageViewAutoLoad, 0, 0);
            drawableId = array.getResourceId(R.styleable.ImageViewAutoLoad_iva_src, -1);
            drawableCircularId = array.getResourceId(R.styleable.ImageViewAutoLoad_iva_circular_src, -1);
            array.recycle();
        }
        if (drawableId > 0) {
            setDrawableId(context, drawableId);
        }
        if (drawableCircularId > 0) {
            setDrawableIdRoundCornerImage(context, drawableCircularId, 10);
        }
//        if (context instanceof ActivityBaseCompat) {
//            ((ActivityBaseCompat) context).addDelegate(this);
//        }
    }

    public void setDrawableId(Context context, final int drawableId) {
        ImageUtil.loadImageByDrawableId(context, drawableId, this);
    }

    public void setDrawableIdRoundCornerImage(Context context, final int drawableId, int dpCorners) {
        ImageUtil.loadDrawableRoundCircleImage(context, drawableId, this, GlideRoundedCornersTransform.CornerType.ALL, dpCorners);
    }

    public void setDrawableIdRoundCornerImage(Context context, final int drawableId, GlideRoundedCornersTransform.CornerType type, int dpCorners) {
        ImageUtil.loadDrawableRoundCircleImage(context, drawableId, this, type, dpCorners);
    }

    public void setDrawableIdRoundCircleImage(Context context, final int drawableId, int radiusByDp) {
        ImageUtil.loadRoundCircleImage(context, drawableId, this, radiusByDp);
    }

    public void setUri(Context context, String uri) {
        if (!TextUtils.isEmpty(uri)) {
            ImageUtil.loadImage(context, uri, this);
        }
    }

    public void setUriRoundCornerImage(Context context, String uri, int dpCorners) {
        ImageUtil.loadRoundCircleImage(context, uri, this, GlideRoundedCornersTransform.CornerType.ALL, dpCorners);
    }

    public void setUriRoundCornerImage(Context context, String uri, GlideRoundedCornersTransform.CornerType type, int radiusByDp) {
        ImageUtil.loadRoundCircleImage(context, uri, this, type, radiusByDp);
    }

    public void setUriRoundCircleImage(Context context, String url, int radiusByDp) {
        ImageUtil.loadRoundCircleImage(context, url, this, radiusByDp);
    }
}

