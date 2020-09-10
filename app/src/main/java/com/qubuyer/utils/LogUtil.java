package com.qubuyer.utils;

import com.orhanobut.logger.Logger;

/**
 * @date 创建时间:2018/12/21
 * @author Susong
 * @description 打印日志类
 & @version
 */
public class LogUtil {

    public static final void d(String msg) {
        Logger.d(msg);
    }

    public static final void e(String msg) {
        Logger.e(msg);
    }

    public static final void i(String msg) {
        Logger.i(msg);
    }

    public static final void w(String msg) {
        Logger.w(msg);
    }
}
