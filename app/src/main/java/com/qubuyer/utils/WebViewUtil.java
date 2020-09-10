package com.qubuyer.utils;

import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.Utils;

/**
 * webview工具类
 */
public class WebViewUtil {
    public static void initWebView(WebView webView, int cacheMode) {
        String cacheDirPath = FileUtil.getOwnCacheDirectory(Utils.getApp(), "temp") + "/webViewCache";
        WebSettings settings = webView.getSettings();
        String ua = settings.getUserAgentString();
//        settings.setUserAgentString(ua + "/HMSH " + SystemConfigUtils.getVersionCode());
        settings.setCacheMode(cacheMode);
        //开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        //设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath);
        //设置Application Caches 缓存目录
        settings.setAppCachePath(cacheDirPath);
        //开启Application H5 Caches 功能
        settings.setAppCacheEnabled(true);
        settings.setSavePassword(true);
        settings.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT > 7) {
            settings.setBlockNetworkLoads(false);
        }
        settings.setAllowFileAccess(true);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setDefaultTextEncodingName("utf-8");// 已utf-8格式解析
//        settings.setPluginsEnabled(true);//设置webview支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.requestFocus();
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public static String getHtmlData(String bodyHTML) {
        String head = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"><style>img{max-width: 100%; width:auto; height:auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
