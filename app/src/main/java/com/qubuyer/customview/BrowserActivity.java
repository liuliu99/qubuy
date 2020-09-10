package com.qubuyer.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.mine.activity.MineLotteryRecordListActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.WebViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间2019/4/8
 * @description 用于显示H5内容
 * @version
 */
public class BrowserActivity extends BaseActivity {
    @BindView(R.id.rwv_view)
    public PowerWebView mWebView;
    /**
     * 一级跳转URL
     */
    public static final String KEY_URL = "default_url";
    /**
     * 是否退出
     */
    private boolean isBackFinish;
    /**
     * 跳转类型
     */
    public static final String KEY_TO_WEBVIEW_TYPE = "to_type";
    /**
     * 默认标题
     */
    public static final String KEY_DEFALT_TITLE = "default_title";
    /**
     * 是否要跟随内容更换标题
     */
    public static final String KEY_NEED_UPDATE_TITLE = "need_update_title";
    /**
     * 是否显示右侧菜单
     */
    public static final String KEY_SHOW_MENU = "show_menu";
    /**
     * 打开下载
     */
    public static final String KEY_DOWNLOAD_ENABLED = "download_enabled";
    /**
     * 额外数据
     */
    public static final String KEY_EXTRA_DATA = "extra_data";
    /**
     * 缓存模式
     */
    public static final String KEY_CACHE_MODE = "cache_mode";
    /**
     * 图片url
     */
    public static final String KEY_IMAGE_URL = "image_url";
    /**
     * 跳转的类型
     */
    private String toType;

    //要加载的url
    private String url;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_browser;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        WebViewUtil.initWebView(mWebView, getIntent().getIntExtra(KEY_CACHE_MODE, WebSettings.LOAD_NO_CACHE));
        toType = getIntent().getStringExtra(KEY_TO_WEBVIEW_TYPE);
        String title = getIntent().getStringExtra(KEY_DEFALT_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        boolean download_enabled = getIntent().getBooleanExtra(KEY_DOWNLOAD_ENABLED, true);
        if (download_enabled) {
            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            });
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (null != mWebView && null != mWebView.getSettings() && !mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
                if (needUpdateTitle() && null != view && !TextUtils.isEmpty(view.getTitle())) {
                    if (view.getTitle().contains(NetConstant.ONLINE_SERVICE_URL)) {
                        setTitle("在线客服");
                    } else {
                        setTitle(view.getTitle());
                    }
                }
//                if (!TextUtils.isEmpty(toType)) {
//                    switch (toType) {
//                        case "from_fragment_main_top":
//                        case "from_fragment_main_gift":
//                        case "from_activity_payment_result":
//                        case "from_fragment_main_ad":
//                            view.loadUrl("javascript:window.java_obj.getSource(document.getElementsByName('description')[0].content);");
//                            break;
//                        case "from_fragment_main_top_insurance":
//                            view.loadUrl("javascript:window.java_obj.getSource(document.getElementsByName('params')[0].content);");
//                            break;
//                        case "from_h5_maotai_start":
//                            toType = "from_h5_maotai_end";
//                            inflateMenu(R.menu.menu_fragment_person_account_detail);
//                            view.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
//                            view.loadUrl("javascript:window.java_obj.getSource(document.getElementsByName('description')[0].content);");
//                            break;
//                        case "from_h5_maotai_end":
//                            toType = "";
//                            clearMenu();
//                            break;
//                    }
//                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                    if (overrideUrlLoadding(url)) {
                        return;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                    if (overrideUrlLoadding(url)) {
                        return true;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        if (getIntent().getBooleanExtra(KEY_SHOW_MENU, false)) {
//            if (!TextUtils.isEmpty(toType)) {
//                switch (toType) {
//                    case "from_fragment_main_top": //从首页轮播图跳转显示网页内容
//                    case "from_fragment_main_gift":
//                    case "from_activity_payment_result":
//                    case "from_fragment_main_top_insurance":
//                    case "from_fragment_main_ad":
//                        inflateMenu(R.menu.menu_fragment_person_account_detail);
//                        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
//                        break;
//                }
//            }
        }
        url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            if (!TextUtils.isEmpty(toType)) {
                switch (toType) {
                    case "from_mine_lottery": //从我的首页抽奖跳转显示网页内容
                        //添加请求头
//                        Map<String, String> extraHeaders = new HashMap<>();
//                        extraHeaders.put("token", HttpManager.getInstance().getToken());
                        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "android");
                        mWebView.loadUrl(url);
                        break;//从我的页面代理商或服务商进入
//                        isBackFinish = true;
                    default:
                        mWebView.loadUrl(url);
                        break;
                }
            } else {
                mWebView.loadUrl(url);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView = null;
    }

    private boolean overrideUrlLoadding(String url) {
        if (url.equalsIgnoreCase("qubuyer://agency_to_main")) {
            EventBusUtil.sendEvent(new GoToMainEvent());
            ActivityUtils.finishOtherActivities(MainActivity.class);
            return true;
        }
        return false;
    }

    private boolean needUpdateTitle() {
        return getIntent().getBooleanExtra(KEY_NEED_UPDATE_TITLE, true);
    }

    protected void onMenuItemClick(MenuItem menuItem) {
        if (!TextUtils.isEmpty(toType)) {
        }
    }

    @Override
    public void onNavigationClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (null != mWebView && mWebView.canGoBack() && !isBackFinish) {
            mWebView.goBack();
        } else {
            this.finish();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    final class InJavaScriptLocalObj {
        //领奖
        @JavascriptInterface
        public void goLuckyDrawViewGet(String letteryId) {
            try {
                JSONObject jsonObject = new JSONObject(letteryId);
                letteryId = jsonObject.optString("body");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isNotEmpty(letteryId)) {
                NavigationUtil.overlay(BrowserActivity.this, MineLotteryRecordListActivity.class, letteryId);
            }
        }
        //我的奖品列表
        @JavascriptInterface
        public void goLuckyDrawView() {
            NavigationUtil.overlay(BrowserActivity.this, MineLotteryRecordListActivity.class);
        }
    }
}
