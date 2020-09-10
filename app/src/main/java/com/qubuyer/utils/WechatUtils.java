package com.qubuyer.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.Utils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

public class WechatUtils {

    private static IWXAPI api; // 相应的包，请集成SDK后自行引入

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled() {
        api = WXAPIFactory.createWXAPI(Utils.getApp(), "Your WeChat AppId");
        if (api.isWXAppInstalled() && api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
            return true;
        } else {
            final PackageManager packageManager = Utils.getApp().getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}