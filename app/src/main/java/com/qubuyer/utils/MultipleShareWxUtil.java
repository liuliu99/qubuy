package com.qubuyer.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qubuyer.base.AM;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class MultipleShareWxUtil {
    //微信包名
    public static final String PACKAGE_NAME_WEI_XIN = "com.tencent.mm";

    /**
     * 分享到微信聊天或者朋友圈
     *
     * @param context
     * @param flag         0:分享给微信好友  1:分享到朋友圈
     * @param files
     * @param kdescription
     */
    public static void shareImage(Context context, final int flag, List<File> files, String kdescription) {
        //判断是否安装微信，如果没有安装微信 又没有判断就直达微信分享是会挂掉的
        if (!isWeixinAvilible(context)) {
            Toast.makeText(context, "您还没有安装微信", Toast.LENGTH_SHORT).show();
        } else {
            ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {

                @Nullable
                @Override
                public Object doInBackground() throws Throwable {
                    try {
                        Intent intent = new Intent();
                        ComponentName comp;
                        if (flag == 0) {
                            comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                            intent.putExtra("Kdescription", kdescription);
                        } else {
                            comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                            intent.putExtra("Kdescription", kdescription);
                        }
                        intent.setComponent(comp);
                        intent.setType("image/*");
                        ArrayList<Uri> imageUris = new ArrayList<Uri>();
                        for (File f : files) {
                            imageUris.add(Uri.fromFile(f));
                        }
                        if (flag == 0) {
                            // 微信分享好友
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                        } else {
                            //跳转到微信
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void onSuccess(@Nullable Object result) {

                }
            });
        }
    }

    public static void shareWebPage(int type, String title, String description, String url, String imageUrl){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;//分享出去的网页地址
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;//分享的标题
        msg.description = description;//分享的描述信息
        //获取网络图片资源
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Glide.with(Utils.getApp()).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                //创建缩略图
                Bitmap thumbBmp = Bitmap.createScaledBitmap(resource, 150, 150, true);
                msg.thumbData = ImageUtils.bitmap2Bytes(thumbBmp, Bitmap.CompressFormat.JPEG, 100);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;

                req.scene = type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                AM.mWxApi.sendReq(req);
            }
        });
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /*** 获取制定包名应用的版本的versionCode     * @param context     * @param     * @return     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
