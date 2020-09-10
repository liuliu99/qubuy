package com.qubuyer.business.good.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.utils.VideoViewUtil;
import com.youth.banner.loader.ImageLoader;

public class GoodDetailBannerLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(((HomeBannerEntity)path).getImageUrl() : ((HomeBannerEntity)path).getImgDrawableId()).apply(RequestOptions.placeholderOf(R.mipmap.ic_logo)).into(imageView);
        HomeGoodEntity.GoodImg entity = (HomeGoodEntity.GoodImg) path;
        String imageUrl = entity.getImage_url();
        if (ObjectUtils.isEmpty(imageUrl)) return;
        if (!imageUrl.contains(".mp4")) {
            Glide.with(context).load(imageUrl).apply(RequestOptions.placeholderOf(R.drawable.placeholder)).into(imageView);
        } else {
            Bitmap bitmap = VideoViewUtil.getVideoThumb(imageUrl, -1, false);
            imageView.setImageBitmap(bitmap);
        }
    }
}
