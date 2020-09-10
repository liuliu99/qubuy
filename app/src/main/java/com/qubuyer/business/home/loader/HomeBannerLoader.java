package com.qubuyer.business.home.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qubuyer.R;
import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.youth.banner.loader.ImageLoader;

public class HomeBannerLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(((HomeBannerEntity)path).getImageUrl() : ((HomeBannerEntity)path).getImgDrawableId()).apply(RequestOptions.placeholderOf(R.mipmap.ic_logo)).into(imageView);
        HomeBannerEntity entity = (HomeBannerEntity) path;
        Glide.with(context).load(entity.getAd_code_full()).apply(RequestOptions.placeholderOf(R.drawable.placeholder)).into(imageView);
    }
}