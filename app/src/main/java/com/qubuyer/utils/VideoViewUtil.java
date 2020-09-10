package com.qubuyer.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.util.HashMap;

public class VideoViewUtil {

    /**
     * 获取视频文件截图(第一帧)
     *
     * @param url   视频文件的路径
     * @param timeUs 微秒(1秒 = 1000毫秒 1毫秒 = 1000微秒)
     * @return Bitmap 返回获取的Bitmap
     */

    public static Bitmap getVideoThumb(String url, long timeUs, boolean isSD) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isSD) {
                //根据文件路径获取缩略图
                retriever.setDataSource(Utils.getApp(), Uri.fromFile(new File(url)));
            } else {
                //根据网络路径获取缩略图
                retriever.setDataSource(url, new HashMap<String, String>());
            }
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(timeUs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
