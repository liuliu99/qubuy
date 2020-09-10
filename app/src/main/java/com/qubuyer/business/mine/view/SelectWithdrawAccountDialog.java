package com.qubuyer.business.mine.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.qubuyer.R;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.activity.ThirdAccountActivity;
import com.qubuyer.customview.BaseDialog;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间:2019/3/4
 * @description 提现说明dialog
 * & @version
 */
public class SelectWithdrawAccountDialog extends BaseDialog {
    private Context mContext;

    @BindView(R.id.rl_wechat)
    RelativeLayout rl_wechat;
    @BindView(R.id.rb_wechat)
    RadioButton rb_wechat;

    @BindView(R.id.rl_alipay)
    RelativeLayout rl_alipay;
    @BindView(R.id.rb_alipay)
    RadioButton rb_alipay;

    @BindView(R.id.ll_add_account)
    LinearLayout ll_add_account;

    private OnSelectWithdrawAccountListener mListener;

    private UserEntity mUserEntity;

    public SelectWithdrawAccountDialog(Context context, UserEntity entity, OnSelectWithdrawAccountListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        mUserEntity = entity;
        setContent();
    }

    public void setEntity(UserEntity entity) {
        if (null != entity) {
            rl_wechat.setVisibility(View.GONE);
            rl_alipay.setVisibility(View.GONE);
            if (entity.getIs_bind_wx() == 1) {
                rl_wechat.setVisibility(View.VISIBLE);
                rb_wechat.setChecked(true);
                rb_alipay.setChecked(false);
            }
            if (entity.getIs_bind_alipay() == 1) {
                rl_alipay.setVisibility(View.VISIBLE);
                if (entity.getIs_bind_wx() != 1) {
                    rb_wechat.setChecked(false);
                    rb_alipay.setChecked(true);
                }
            }
        }
    }

    private void setContent() {
        setContentView(R.layout.layout_dialog_select_withdraw_account);
        ButterKnife.bind(this);
        setEntity(mUserEntity);
    }

    @OnClick({R.id.rl_wechat, R.id.rb_wechat, R.id.rl_alipay, R.id.rb_alipay, R.id.ll_add_account})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.rl_wechat:
            case R.id.rb_wechat:
                rb_wechat.setChecked(true);
                rb_alipay.setChecked(false);
                if (null != mListener) {
                    mListener.onSelecteAccountListener(1);
                }
                break;
            case R.id.rl_alipay:
            case R.id.rb_alipay:
                rb_wechat.setChecked(false);
                rb_alipay.setChecked(true);
                if (null != mListener) {
                    mListener.onSelecteAccountListener(2);
                }
                break;
            case R.id.ll_add_account:
                NavigationUtil.overlay(mContext, ThirdAccountActivity.class);
                dismiss();
                break;
        }
    }

    public interface OnSelectWithdrawAccountListener {
        void onSelecteAccountListener(int type);
    }
}
