package com.qubuyer.base;

import android.os.Build;
import android.os.StrictMode;

import com.blankj.utilcode.util.Utils;
import com.mob.MobSDK;
import com.qubuyer.A;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class AM extends A {

    public static IWXAPI mWxApi;
    public static Tencent mTencent;
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            return new ClassicsHeader(context);//;//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        registerToWX();
        registerToQQ();
        MobSDK.init(this);
        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, "wxada57b82e3e68827", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxada57b82e3e68827");
    }

    private void registerToQQ(){
        mTencent = Tencent.createInstance("1109786854",getApplicationContext());//将123123123改为自己的AppID
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
