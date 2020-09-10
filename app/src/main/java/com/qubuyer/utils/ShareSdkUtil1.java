package com.qubuyer.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qubuyer.R;

import java.io.ObjectStreamException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareSdkUtil1 {
    public static class Builder {
        private static Context context;
        private PlatformActionListener platformActionListener;
        private String text = "";
        private String imageUrl = "";
        private int shareType = Platform.SHARE_WEBPAGE;
        private String url = "";
        private String title = "";
        private String titleUrl = "";

        public static Builder getInstanse(Context ctx) {
            context = ctx;
            return BuildHolder.builder;
        }

        private static class BuildHolder{
            public static final Builder builder = new Builder();
        }

        private Object readResolve() throws ObjectStreamException {
            return BuildHolder.builder;
        }

        private Builder() {

        }

        public Builder setPlatformActionListener(PlatformActionListener platformActionListener) {
            this.platformActionListener = platformActionListener;
            return this;
        }

        /**
         * 分享文本, 不能超过140个汉字
         *
         * @return
         */
        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * 网络图片
         *
         * @param imageUrl
         * @return
         */
        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * 微信（好友、朋友圈、收藏）使用的分享类型
         *
         * @return
         */
        public Builder setShareType(int shareType) {
            this.shareType = shareType;
            return this;
        }

        /**
         * 网页地址
         *
         * @param url
         * @return
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 标题
         *
         * @param title 512Bytes以内
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * QQ 分享时的标题链接
         *
         * @param titleUrl
         * @return
         */
        public Builder setTitleUrl(String titleUrl) {
            this.titleUrl = titleUrl;
            return this;
        }

        public void showShareMenu() {
            View view = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
            final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.DialogBottomAnimation);
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissmiss(popupWindow);
                }
            });
            view.findViewById(R.id.share_wechat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissmiss(popupWindow);
                    shareToWechat(title, text, imageUrl, url);
                }
            });
            view.findViewById(R.id.share_wechate_moment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissmiss(popupWindow);
                    shareToWechatMoments(title, text, imageUrl, url);
                }
            });
            view.findViewById(R.id.share_qq).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissmiss(popupWindow);
                    shareToQQ(title, titleUrl, text, imageUrl);
                }
            });
            popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(1, 0.7f);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(0.7f, 1);
                }
            });
        }

        private void dissmiss(PopupWindow popupWindow) {
            popupWindow.dismiss();
            backgroundAlpha(0.7f, 1);
        }

        private void backgroundAlpha(float bgFromAlpha, float bgToAlpha) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(bgFromAlpha, bgToAlpha);
            valueAnimator.setDuration(context.getResources().getInteger(android.R.integer.config_longAnimTime));
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setWindowBgAlpht((float) animation.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }

        private void setWindowBgAlpht(float alpht) {
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = alpht; //0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp);
        }

        public void shareToSina(String text, String imgUrl) {
            if (text == null || text.equals("")) {
                Toast.makeText(context, "分享文本不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageUrl == null || imageUrl.equals("")) {
                Toast.makeText(context, "分享图片链接地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            this.text = text;
            this.imageUrl = imgUrl;
            share(SinaWeibo.NAME);
        }

        public void shareToWechat(String title, String text, String imageUrl, String url) {
            if (cannotShareToWechat(title, text, imageUrl, url)) return;
            this.title = title;
            this.text = text;
            this.imageUrl = imageUrl;
            this.url = url;
//            share(Wechat.NAME);
            MultipleShareWxUtil.shareWebPage(0, title, text, url, imageUrl);
        }

        public void shareToWechatMoments(String title, String text, String imageUrl, String url) {
            if (cannotShareToWechat(title, text, imageUrl, url)) return;
            this.title = title;
            this.text = text;
            this.imageUrl = imageUrl;
            this.url = url;
//            share(WechatMoments.NAME);
            MultipleShareWxUtil.shareWebPage(1, title, text, url, imageUrl);
        }

        private boolean cannotShareToWechat(String title, String text, String imageUrl, String url) {
            if (text == null || text.equals("")) {
                Toast.makeText(context, "分享文本不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (imageUrl == null || imageUrl.equals("")) {
                Toast.makeText(context, "分享图片链接地址不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (title == null || title.equals("")) {
                Toast.makeText(context, "分享标题不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (url == null || url.equals("")) {
                Toast.makeText(context, "分享链接地址不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        public void shareToQQ(String title, String titleUrl, String text, String imageUrl) {
            if (text == null || text.equals("")) {
                Toast.makeText(context, "分享文本不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageUrl == null || imageUrl.equals("")) {
                Toast.makeText(context, "分享图片链接地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (title == null || title.equals("")) {
                Toast.makeText(context, "分享标题不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (titleUrl == null || titleUrl.equals("")) {
                Toast.makeText(context, "分享标题链接地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            this.title = title;
            this.titleUrl = titleUrl;
            this.text = text;
            this.imageUrl = imageUrl;
            share(QQ.NAME);
        }

        public void share(String platformName) {
            if (text == null || text.equals("")) {
                Toast.makeText(context, "分享文本不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Platform.ShareParams sp = new Platform.ShareParams();
            if (platformName.equals(Wechat.NAME) || platformName.equals(WechatMoments.NAME)) {
                sp.setShareType(shareType);
            }
            sp.setText(text);
            sp.setImageUrl(imageUrl);
            sp.setUrl(url);
            sp.setTitle(title);
            sp.setTitleUrl(titleUrl);
            Platform platform = ShareSDK.getPlatform(platformName);
            platform.setPlatformActionListener(platformActionListener); // 设置分享事件回调
            //要数据不要功能，主要体现在不会重复出现授权界面
            platform.showUser(null);
            // 执行图文分享
            platform.share(sp);
        }
    }
}
