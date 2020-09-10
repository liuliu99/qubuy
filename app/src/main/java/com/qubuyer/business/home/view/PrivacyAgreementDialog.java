package com.qubuyer.business.home.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.qubuyer.R;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BaseDialog;
import com.qubuyer.customview.BrowserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Susong
 * @date 创建时间2019/12/21
 * @description 隐私协议弹框Dialog
 * @version
 */
public class PrivacyAgreementDialog extends BaseDialog {
    private Context mContext;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_protocol)
    TextView tv_protocol;

    @BindView(R.id.tv_left_btn)
    TextView tv_left_btn;
    @BindView(R.id.tv_right_btn)
    TextView tv_right_btn;

    private PrivacyAgreementListener mListener;

    public PrivacyAgreementDialog(Context context, PrivacyAgreementListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        setContent();
    }

    private void setContent() {
        setContentView(R.layout.layout_dialog_privacy_agreement);
        ButterKnife.bind(this);
        setCancelable(false);

        SpanUtils.with(tv_protocol).append("感谢您的信任并使用趣买买！我们依据最新的法规及监管政策要求，更新了").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .append("《趣买买用户协议》").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_light))
                .append("及").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .append("《趣买买隐私政策》").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_light))
                .append("，特此向您推送此说明，以便继续为您服务。").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .appendLine()
                .append("请仔细阅读《趣买买用户协议》（也可称为“服务条款”）并理解相关条款内容，在确认充分理解并同意后使用趣买买相关产品或服务。点击同意及代表您已阅读").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .append("《趣买买用户协议》").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_light))
                .append("及").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .append("《趣买买隐私政策》").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_light))
                .append("。如果您不同意，将可能影响使用趣买买相关产品或服务。").setFontSize(ConvertUtils.dp2px(15)).setForegroundColor(ContextCompat.getColor(mContext, R.color.common_text_color1))
                .create();
        tv_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/helpCenterDetail?id=44");
                mContext.startActivity(intent);
            }
        });
        tv_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishAllActivities();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        tv_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
               if (null != mListener) {
                   mListener.onIsAgree(true);
               }
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    public interface PrivacyAgreementListener {
        void onIsAgree(boolean isAgree);
    }
}
