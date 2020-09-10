package com.qubuyer.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.qubuyer.R;

/**
 * @author Susong
 * @date 创建时间2019/3/25
 * @description 如果要为WebView增加更多定制功能的话，在这个类中添加相应方法即可
 * @version
 */
public class PowerWebView extends WebView {
    private ProgressBar mProgressBar;

    private Handler mHandler = new Handler();

    public PowerWebView(Context context) {
        super(context);
        init(context);
    }

    public PowerWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PowerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PowerWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 9, 0, 0));
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar));
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                super.onProgressChanged(view, newProgress);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (100 == newProgress) {
                            mProgressBar.setVisibility(GONE);
                        } else {
                            if (VISIBLE != mProgressBar.getVisibility()) {
                                mProgressBar.setVisibility(View.VISIBLE);
                            }
                            mProgressBar.setProgress(newProgress);
                        }
                    }
                });
                super.onProgressChanged(view, newProgress);
            }
        });
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
