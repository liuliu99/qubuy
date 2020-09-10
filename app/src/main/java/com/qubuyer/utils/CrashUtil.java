package com.qubuyer.utils;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;
import com.qubuyer.constant.AppConstant;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @date 创建时间:2018/12/30
 * @author Susong
 * @description Bugly程序崩溃上报类
& @version
 */
public class CrashUtil {
    @SuppressLint("MissingPermission")
    public static void initCrashReport() {
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(Utils.getApp());
        userStrategy.setAppChannel(AppUtil.getChannel());
        userStrategy.setAppVersion(AppUtils.getAppVersionName());
        userStrategy.setDeviceID(PhoneUtils.getIMEI());
        userStrategy.setAppReportDelay(5000);
        /** 自动初始化开关: true表示app启动自动初始化升级模块; false不会自动初始化; 开发者如果担心sdk初始化影响app启动速度，可以设置为false，在后面某个时刻手动调用Beta.init(getApplicationContext(),false);*/
        Beta.autoInit = true;
        /** 自动检查更新开关: true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;*/
        Beta.autoCheckUpgrade = false;
        /** 延迟初始化: 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度; */
        Beta.initDelay = 3 * 1000;
        /** 设置sd卡的Download为更新资源存储目录 */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /** 设置开启显示打断策略 */
        Beta.showInterruptedStrategy = true;
//        Beta.upgradeCheckPeriod = 60 * 1000;
        if(AppConstant.DEBUG) {
//            CrashReport.initCrashReport(application, "900024304", Constants.DEBUG, userStrategy);
            Bugly.init(Utils.getApp(), "6c82dd6531", AppConstant.DEBUG, userStrategy);
        } else {
//            CrashReport.initCrashReport(application, "900005666", Constants.DEBUG, userStrategy);
            Bugly.init(Utils.getApp(), "eaf71f044e", AppConstant.DEBUG, userStrategy);
        }
    }

    /**
     * 上报捕捉到的异常到Bugly
     */
    public static void postCatchedException(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        CrashReport.postCatchedException(throwable);
    }
}
