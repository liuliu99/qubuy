package com.qubuyer.business.login.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.business.login.presenter.LoginPresenter;
import com.qubuyer.business.register.activity.RegisterActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.CountDownTimerUtils;
import com.qubuyer.utils.NavigationUtil;

/**
 * 手机验证码登录ViewPag
 */
public class LoginPhoneViewPage extends LoginBaseViewPage implements ILoginView {
    //当前上下文
    private Context mContext;
    private View mView;

    private EditText et_phone;
    private ImageViewAutoLoad iv_phone_delete;
    private EditText et_code;
    private ImageViewAutoLoad iv_code_delete;
    private TextView tv_get_code;
    private TextView tv_login;
    private CountDownTimerUtils mCountDownTimer;
    private LoginPresenter mPresenter;

    public LoginPhoneViewPage(Context mContext) {
        this.mContext = mContext;
        createP();
    }

    protected void createP() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_login_phone_page, null);
            et_phone = mView.findViewById(R.id.et_phone);
            et_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkInput();
                }
            });
            et_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        iv_phone_delete.setVisibility(View.VISIBLE);
                    } else {
                        iv_phone_delete.setVisibility(View.GONE);
                    }
                    if (s.length() >= 11) {
                        tv_get_code.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_login_get_code_input_bg));
                        tv_get_code.setEnabled(true);
                        tv_get_code.setTextColor(Color.parseColor("#878787"));
                    } else {
                        tv_get_code.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_login_get_code_normal_bg));
                        tv_get_code.setEnabled(false);
                        tv_get_code.setTextColor(Color.parseColor("#CCCCCC"));
                    }
                }
            });
            iv_phone_delete = mView.findViewById(R.id.iv_phone_delete);
            iv_phone_delete.setVisibility(View.GONE);
            iv_phone_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_phone_delete.setVisibility(View.GONE);
                    et_phone.setText("");
                }
            });
            et_code = mView.findViewById(R.id.et_code);
            et_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkInput();
                }
            });
            et_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_phone.getText().length() <= 0) {
                        ToastUtils.showShort("请先输入手机号");
                        et_code.clearFocus();
                        et_code.setCursorVisible(false);
                        KeyboardUtils.hideSoftInput((Activity) mContext);
                    } else {
                        et_code.requestFocus();
                        et_code.setCursorVisible(true);
                    }
                }
            });
            et_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        iv_code_delete.setVisibility(View.VISIBLE);
                    } else {
                        iv_code_delete.setVisibility(View.GONE);
                    }
                }
            });
            iv_code_delete = mView.findViewById(R.id.iv_code_delete);
            iv_code_delete.setVisibility(View.GONE);
            iv_code_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_code_delete.setVisibility(View.GONE);
                    et_code.setText("");
                }
            });
            tv_get_code = mView.findViewById(R.id.tv_get_code);
            mCountDownTimer = new CountDownTimerUtils(tv_get_code, mContext);
            tv_get_code.setEnabled(false);
            tv_get_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_get_code.setEnabled(false);//设置不可点击
                    mPresenter.getVerificationcode(et_phone.getText().toString());
                }
            });
            tv_login = mView.findViewById(R.id.tv_login);
            tv_login.setEnabled(false);
            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = et_phone.getText().toString();
                    String code = et_code.getText().toString();
                    mPresenter.loginWithPhoneCode(phone, code);
                }
            });
            mView.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, RegisterActivity.class);
                }
            });
        }
        return mView;
    }

    @Override
    public void destory() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void showLoading() {
        ((BaseActivity)mContext).showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity)mContext).dissmissLoadingDialog();
    }

    @Override
    public void doResponseError(int code, String message) {
        ((BaseActivity) mContext).doResponseError(code, message);
    }

    private void checkInput() {
        tv_login.setEnabled(false);
        tv_login.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_login_btn_normal_bg));
        if (TextUtils.isEmpty(et_phone.getText())) return;
        if (TextUtils.isEmpty(et_code.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowLoginResultToView(LoginEntity result) {
        if (null != result && !TextUtils.isEmpty(result.getToken())) {
//            EventBus.getDefault().post(new LoginResultEvent());
            ((BaseActivity)mContext).finish();
        }
    }

    @Override
    public void showWechatOrQQLoginSuccessView(ThirdLoginEntity entity) {

    }

    @Override
    public void showWechatOrQQLoginNoBindView(ThirdLoginEntity result) {

    }

    @Override
    public void showWechatOrQQLoginToSetPwdView(ThirdLoginEntity result) {

    }

    @Override
    public void showWechatOrQQLoginBindFailView(ThirdLoginEntity result) {

    }

    @Override
    public void showWechatOrQQLoginRegisterSuccessView(ThirdLoginEntity result) {

    }

    @Override
    public void onShowVerificationcodeResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("验证码获取成功");
            tv_get_code.setEnabled(false);
            tv_get_code.setBackground(mContext.getResources().getDrawable(R.drawable.shape_login_get_code_normal_bg));
            tv_get_code.setTextColor(Color.parseColor("#CCCCCC"));
            mCountDownTimer.start();
        } else {
            tv_get_code.setEnabled(true);
            tv_get_code.setBackground(mContext.getResources().getDrawable(R.drawable.shape_login_get_code_input_bg));
            tv_get_code.setTextColor(Color.parseColor("#878787"));
        }
    }
}


