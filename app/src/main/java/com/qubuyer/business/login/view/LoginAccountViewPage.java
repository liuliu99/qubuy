package com.qubuyer.business.login.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.business.login.presenter.LoginPresenter;
import com.qubuyer.business.register.activity.ForgetPwdActivity;
import com.qubuyer.business.register.activity.RegisterActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.NavigationUtil;

/**
 * 账号密码登录ViewPag
 */
public class LoginAccountViewPage extends LoginBaseViewPage implements ILoginView {
    //当前上下文
    private Context mContext;
    private View mView;

    EditText et_phone;
    ImageViewAutoLoad iv_phone_delete;
    EditText et_pwd;
    ImageViewAutoLoad iv_pwd_show;
    TextView tv_login;

    private boolean mIsHidePwd;
    private boolean mIsClickHidePwd;

    private LoginPresenter mPresenter;

    public LoginAccountViewPage(Context mContext) {
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
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_login_account_page, null);
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
            et_pwd = mView.findViewById(R.id.et_pwd);
            et_pwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s) && !mIsClickHidePwd) {
                        iv_pwd_show.setDrawableId(mContext, R.drawable.icon_login_show_pwd);
                    } else if (TextUtils.isEmpty(s) && mIsClickHidePwd) {
                        mIsClickHidePwd = false;
                        iv_pwd_show.setDrawableId(mContext, R.drawable.icon_login_hide_pwd);
                        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        iv_pwd_show.setDrawableId(mContext, R.drawable.icon_login_hide_pwd);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkInput();
                }
            });
            iv_pwd_show = mView.findViewById(R.id.iv_pwd_show);
            iv_pwd_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsClickHidePwd = true;
                    if (!mIsHidePwd) {
                        mIsHidePwd = true;
                        iv_pwd_show.setDrawableId(mContext, R.drawable.icon_login_hide_pwd);
                        et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        et_pwd.setSelection(et_pwd.getText().length());
                    } else {
                        mIsHidePwd = false;
                        iv_pwd_show.setDrawableId(mContext, R.drawable.icon_login_show_pwd);
                        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        et_pwd.setSelection(et_pwd.getText().length());
                    }
                }
            });
            tv_login = mView.findViewById(R.id.tv_login);
            tv_login.setEnabled(false);
            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = et_phone.getText().toString();
                    String pwd = et_pwd.getText().toString();
                    mPresenter.login(phone, pwd);
                }
            });
            mView.findViewById(R.id.tv_forget_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, ForgetPwdActivity.class);
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
        if (TextUtils.isEmpty(et_pwd.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void destory() {
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

    }
}


