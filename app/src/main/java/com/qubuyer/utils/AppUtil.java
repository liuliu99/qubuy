package com.qubuyer.utils;

import com.blankj.utilcode.util.Utils;
import com.meituan.android.walle.WalleChannelReader;

public class AppUtil {
    /**
     * 获取渠道号
     */
    public static String getChannel() {
//        return getMetaData("UMENG_CHANNEL", Integer.class);
//        return String.valueOf(BuildConfig.channel);
        return WalleChannelReader.getChannel(Utils.getApp());
    }
}
