package com.qubuyer.business.payment.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.customview.BaseDialog;
import com.qubuyer.customview.payment.GridPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @date 创建时间:2019/3/2
 * @author Susong
 * @description 支付密码Dialog
 & @version
 */
public class PayPwdDialog extends BaseDialog {
    private Context mContext;

    @BindView(R.id.tv_withdraw_title)
    TextView tv_withdraw_title;
    @BindView(R.id.tv_withdraw_money)
    TextView tv_withdraw_money;
    //输入支付密码View
    @BindView(R.id.gpv_pwd)
    GridPasswordView gpv_pwd;

    private PayPwdListener mListener;

    //用户输入的密码
    private String mInputPwd = "";

    public PayPwdDialog(Context context) {
        super(context);
        mContext = context;
        setContent();
    }

    private void setContent() {
        setContentView(R.layout.layout_dialog_member_pay_pwd);
        ButterKnife.bind(this);
        tv_withdraw_money.setText(changWithdrawMoneyText("¥99"));
        gpv_pwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onChanged(String psw) {
                if (psw.length() >= 6 && null != mListener) {
                    mListener.onInputPayPwdListener(psw);
                }
            }

            @Override
            public void onMaxLength(String psw) {
                // 获取密码
                mInputPwd = psw;
            }
        });
    }

    @OnClick({R.id.iv_close})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        if (null != gpv_pwd) {
            gpv_pwd.clearPassword();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mInputPwd = "";
    }

    private SpannableStringBuilder changWithdrawMoneyText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(13)), 0, value.indexOf("¥") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(30)), value.indexOf("¥") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    public void setListener(PayPwdListener mListener) {
        this.mListener = mListener;
    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)) {
            tv_withdraw_title.setText(title);
        }
    }

    public interface PayPwdListener {
        void onInputPayPwdListener(String pwd);
    }
}
