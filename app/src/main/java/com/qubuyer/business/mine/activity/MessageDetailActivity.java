package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.presenter.MessageDetailPresenter;
import com.qubuyer.business.mine.view.IMessageDetailView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.utils.WebViewUtil;

import butterknife.BindView;

/**
 * @date 创建时间:2019/3/12
 * @author Susong
 * @description 消息详情
 & @version
 */
public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements IMessageDetailView {
//    @BindView(R.id.tv_title)
//    TextView tv_title;
//    @BindView(R.id.tv_time)
//    TextView tv_time;
    @BindView(R.id.wv_content)
    WebView wv_content;

    private int mMessageId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_message_detail;
    }

    @Override
    protected MessageDetailPresenter createP(Context context) {
        return new MessageDetailPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mMessageId = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setTitle("消息详情");
        WebViewUtil.initWebView(wv_content, WebSettings.LOAD_NO_CACHE);
    }

    @Override
    protected void initData() {
        mPresenter.getMessageDetailInfo(mMessageId);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onShowMessageDetailToView(String entitiy) {
        if (null != entitiy) {
//            tv_title.setText(entitiy.getTitle());
//            tv_time.setText(entitiy.getCreate_time());
            WebViewUtil.initWebView(wv_content, WebSettings.LOAD_NO_CACHE);
            wv_content.loadDataWithBaseURL(NetConstant.BASE_URL, entitiy, "text/html", "utf-8", null);
        }
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:10px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
