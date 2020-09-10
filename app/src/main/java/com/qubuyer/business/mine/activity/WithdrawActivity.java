package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.WithdrawPresenter;
import com.qubuyer.business.mine.view.IWithdrawView;
import com.qubuyer.business.mine.view.SelectWithdrawAccountDialog;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.EditTextUtils;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StringUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/13
 * @description 提现activity
 * & @version
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements IWithdrawView {
    @BindView(R.id.ll_add_account)
    LinearLayout ll_add_account;

    @BindView(R.id.rl_thrid_account)
    RelativeLayout rl_thrid_account;
    @BindView(R.id.iv_withdraw_title)
    ImageViewAutoLoad iv_withdraw_title;
    @BindView(R.id.tv_withdraw_title)
    TextView tv_withdraw_title;
    @BindView(R.id.et_withdraw_money)
    EditText et_withdraw_money;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.tv_service_charge)
    TextView tv_service_charge;
    @BindView(R.id.v_line)
    View v_line;
    @BindView(R.id.tv_real_money)
    TextView tv_real_money;
    @BindView(R.id.tv_confirm_withdraw)
    TextView tv_confirm_withdraw;

    private SelectWithdrawAccountDialog selectWithdrawAccountDialog;

    //选择的提现类型 1:微信 2:支付宝
    private int mSelectedWithdrawType = -1;

    private UserEntity mUserEntity;
    private WalletInfoEntity mWalletInfoEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_withdraw;
    }

    @Override
    protected WithdrawPresenter createP(Context context) {
        return new WithdrawPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("提现");
        EditTextUtils.setPricePoint(et_withdraw_money);
        tv_service_charge.setVisibility(View.GONE);
        v_line.setVisibility(View.GONE);
        tv_real_money.setVisibility(View.GONE);
        et_withdraw_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcServiceCharge(s.toString());
            }
        });
    }

    private void calcServiceCharge(String inputWithdrawMoney) {
        if (ObjectUtils.isEmpty(mUserEntity)) return;
        String serviceCharge = mUserEntity.getWithdrawal_cash();
        if (ObjectUtils.isEmpty(inputWithdrawMoney) || ObjectUtils.isEmpty(serviceCharge)) {
            tv_service_charge.setVisibility(View.GONE);
            v_line.setVisibility(View.GONE);
            tv_real_money.setVisibility(View.GONE);
            return;
        }
        tv_service_charge.setVisibility(View.VISIBLE);
        v_line.setVisibility(View.VISIBLE);
        tv_real_money.setVisibility(View.VISIBLE);
        float serviceChargeFloat = Float.parseFloat(serviceCharge);
        float withdrawMoney = Float.parseFloat(inputWithdrawMoney);
        float serviceChargeCalcFloat = withdrawMoney * (serviceChargeFloat / 100);
        SpanUtils.with(tv_service_charge).append(String.format(Locale.CHINA, "服务费:%.2f元", serviceChargeCalcFloat)).setFontSize(ConvertUtils.dp2px(14)).setForegroundColor(ContextCompat.getColor(this, R.color.common_text_color1))
                .appendSpace(ConvertUtils.dp2px(5))
                .append(String.format(Locale.CHINA, "(费率:%.2f%%)", serviceChargeFloat)).setFontSize(ConvertUtils.dp2px(14)).setForegroundColor(Color.parseColor("#999999"))
                .create();
        SpanUtils.with(tv_real_money).append("实际到账:").setFontSize(ConvertUtils.dp2px(14)).setForegroundColor(ContextCompat.getColor(this, R.color.common_text_color1))
                .appendSpace(ConvertUtils.dp2px(2))
                .append(String.format(Locale.CHINA, "%.0f元", withdrawMoney - serviceChargeCalcFloat)).setFontSize(ConvertUtils.dp2px(14)).setForegroundColor(Color.parseColor("#FE761C"))
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }

    private void initWithdrawAccountInfo(UserEntity entity) {
        if (null != entity) {
            if (entity.getIs_bind_wx() == 1) {
                ll_add_account.setVisibility(View.GONE);
                rl_thrid_account.setVisibility(View.VISIBLE);
                iv_withdraw_title.setDrawableId(this, R.drawable.icon_withdraw_wechat);
                tv_withdraw_title.setText("微信支付");
                mSelectedWithdrawType = 1;
            } else if (entity.getIs_bind_alipay() == 1) {
                ll_add_account.setVisibility(View.GONE);
                rl_thrid_account.setVisibility(View.VISIBLE);
                iv_withdraw_title.setDrawableId(this, R.drawable.icon_withdraw_alipay);
                tv_withdraw_title.setText("支付宝");
                mSelectedWithdrawType = 2;
            } else {
                ll_add_account.setVisibility(View.VISIBLE);
                rl_thrid_account.setVisibility(View.GONE);
                mSelectedWithdrawType = -1;
            }
        }
    }

    @Override
    protected void initData() {
        mPresenter.getWalletInfo();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.ll_add_account, R.id.rl_thrid_account, R.id.tv_all_withdraw, R.id.tv_confirm_withdraw, R.id.tv_get_code})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.ll_add_account: //添加提现账户
                NavigationUtil.overlay(this, ThirdAccountActivity.class);
                break;
            case R.id.rl_thrid_account: //三方账户
                if (null == selectWithdrawAccountDialog) {
                    selectWithdrawAccountDialog = new SelectWithdrawAccountDialog(this, mUserEntity, new SelectWithdrawAccountDialog.OnSelectWithdrawAccountListener() {
                        @Override
                        public void onSelecteAccountListener(int type) {
                            mSelectedWithdrawType = type;
                            switch (type) {
                                case 1:
                                    iv_withdraw_title.setDrawableId(WithdrawActivity.this, R.drawable.icon_withdraw_wechat);
                                    tv_withdraw_title.setText("微信支付");
                                    selectWithdrawAccountDialog.dismiss();
                                    break;
                                case 2:
                                    iv_withdraw_title.setDrawableId(WithdrawActivity.this, R.drawable.icon_withdraw_alipay);
                                    tv_withdraw_title.setText("支付宝");
                                    selectWithdrawAccountDialog.dismiss();
                                    break;
                            }
                        }
                    });
                }
                if (!selectWithdrawAccountDialog.isShowing()) {
                    selectWithdrawAccountDialog.show();
                }
                break;
            case R.id.tv_all_withdraw: //全部提现
                if (null != mWalletInfoEntity && !TextUtils.isEmpty(mWalletInfoEntity.getMoney())) {
                    et_withdraw_money.setText(StringUtil.formatAmount(mWalletInfoEntity.getMoney(), 2));
                    et_withdraw_money.setSelection(et_withdraw_money.getText().length());
                }
                break;
            case R.id.tv_get_code: //获取验证码
                mPresenter.getVerificationcode(mUserEntity.getMobile());
                break;
            case R.id.tv_confirm_withdraw: //确定提现
                if (mSelectedWithdrawType == -1) {
                    ToastUtils.showShort("请选择提现账户");
                    return;
                }
                String withdrawMoney = et_withdraw_money.getText().toString();
                if (TextUtils.isEmpty(withdrawMoney)) {
                    ToastUtils.showShort("请输入提现金额");
                    return;
                }
                String code = et_code.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showShort("请输入手机验证码");
                    return;
                }
                mPresenter.withdraw(withdrawMoney, mSelectedWithdrawType == 1 ? "wx" : "alipay", mUserEntity.getMobile(), code);
                break;
        }
    }

    @Override
    public void onShoWalletInfoToView(WalletInfoEntity entity) {
        mWalletInfoEntity = entity;
        if (null == entity) return;
        tv_balance.setText("可提现余额¥" + StringUtil.formatAmount(entity.getMoney(), 2));
    }

    @Override
    public void onShowUserInfoToView(UserEntity entity) {
        mUserEntity = entity;
        if (null != entity) {
            SessionUtil.getInstance().saveUserInfo(entity);
            initWithdrawAccountInfo(entity);
            if (null != selectWithdrawAccountDialog) {
                selectWithdrawAccountDialog.setEntity(entity);
            }
        }
    }

    @Override
    public void onShowVerificationcodeResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("验证码获取成功");
        }
    }

    @Override
    public void onShowWithdrawResultToView(boolean result) {
        if (result) {
            NavigationUtil.overlay(this, WithdrawResultActivity.class);
        }
    }
}
