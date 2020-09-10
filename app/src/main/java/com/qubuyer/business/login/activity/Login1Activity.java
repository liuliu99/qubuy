package com.qubuyer.business.login.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.AM;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.business.login.presenter.LoginPresenter;
import com.qubuyer.business.login.view.ILoginView;
import com.qubuyer.business.register.activity.BindingActivity;
import com.qubuyer.business.register.activity.ForgetPwdActivity;
import com.qubuyer.business.register.activity.RegisterActivity;
import com.qubuyer.business.register.activity.SetPwdActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.NavigationUtil;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class Login1Activity extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_phone_delete)
    ImageViewAutoLoad iv_phone_delete;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_pwd_show)
    ImageViewAutoLoad iv_pwd_show;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private boolean mIsHidePwd;
    private boolean mIsClickHidePwd;

    private BaseUiListener mListener;

    //选中的绑定第三方类型 0:微信 1:QQ
    private int mSelectedBindType = -1;
    //选中的第三方openid
    private String mSelectedBindOpenId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_login1;
    }

    @Override
    protected LoginPresenter createP(Context context) {
        return new LoginPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
        mListener = new BaseUiListener();
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
        iv_phone_delete.setVisibility(View.GONE);
        iv_phone_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_phone_delete.setVisibility(View.GONE);
                et_phone.setText("");
            }
        });
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !mIsClickHidePwd) {
                    iv_pwd_show.setDrawableId(Login1Activity.this, R.drawable.icon_login_show_pwd);
                } else if (TextUtils.isEmpty(s) && mIsClickHidePwd) {
                    mIsClickHidePwd = false;
                    iv_pwd_show.setDrawableId(Login1Activity.this, R.drawable.icon_login_hide_pwd);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    iv_pwd_show.setDrawableId(Login1Activity.this, R.drawable.icon_login_hide_pwd);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInput();
            }
        });
        iv_pwd_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsClickHidePwd = true;
                if (!mIsHidePwd) {
                    mIsHidePwd = true;
                    iv_pwd_show.setDrawableId(Login1Activity.this, R.drawable.icon_login_hide_pwd);
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
                } else {
                    mIsHidePwd = false;
                    iv_pwd_show.setDrawableId(Login1Activity.this, R.drawable.icon_login_show_pwd);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
                }
            }
        });
        tv_login.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSelectedBindType == 0) {
            String openId = SPUtils.getInstance().getString(AppConstant.WECHAT_OPEN_ID);
            if (!TextUtils.isEmpty(openId)) {
                mSelectedBindOpenId = openId;
                mPresenter.wecahtOrQQLogin("wx", openId);
                SPUtils.getInstance().remove(AppConstant.WECHAT_OPEN_ID);
            }
        }
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.iv_close, R.id.tv_forget_password, R.id.tv_register_title, R.id.tv_register, R.id.tv_login, R.id.ll_wechat_login,R.id.ll_qq_login})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_close: //关闭页面
                finish();
                break;
            case R.id.tv_forget_password: //忘记密码
                NavigationUtil.overlay(this, ForgetPwdActivity.class);
                break;
            case R.id.tv_register_title:
            case R.id.tv_register: //立即注册
                NavigationUtil.overlay(this, RegisterActivity.class);
                break;
            case R.id.tv_login: //登录
                String phone = et_phone.getText().toString();
                String pwd = et_pwd.getText().toString();
                mPresenter.login(phone, pwd);
                break;
            case R.id.ll_wechat_login: //微信登录
                mSelectedBindType = 0;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "snsapi_login_lnint";
                AM.mWxApi.sendReq(req);
                break;
            case R.id.ll_qq_login: //QQ登录
                mSelectedBindType = 1;
                //如果session不可用，则登录，否则说明已经登录
                AM.mTencent.login(this, "all", new BaseUiListener());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, mListener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    private void checkInput() {
        tv_login.setEnabled(false);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_normal_bg));
        if (TextUtils.isEmpty(et_phone.getText())) return;
        if (TextUtils.isEmpty(et_pwd.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowLoginResultToView(LoginEntity entity) {
        if (null != entity && !TextUtils.isEmpty(entity.getToken())) {
//            EventBus.getDefault().post(new LoginResultEvent());
            finish();
        }
    }

    @Override
    public void showWechatOrQQLoginSuccessView(ThirdLoginEntity entity) {
        finish();
    }

    @Override
    public void showWechatOrQQLoginNoBindView(ThirdLoginEntity result) {
        HashMap map = new HashMap();
        map.put("bind_type", mSelectedBindType);
        map.put("bind_openid", result.getOpenid());
        NavigationUtil.forward(this, BindingActivity.class, map);
    }

    @Override
    public void showWechatOrQQLoginToSetPwdView(ThirdLoginEntity result) {
        NavigationUtil.forward(this, SetPwdActivity.class);
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

    private class BaseUiListener implements IUiListener {
    //这个类需要实现三个方法 onComplete（）：登录成功需要做的操作写在这里
    // onError onCancel 方法具体内容自己搜索
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.showShort("返回为空, 登录失败");
                return;
            }
            JSONObject jb = (JSONObject) response;
            if (jb.length() == 0) {
                ToastUtils.showShort("返回为空, 登录失败");
                return;
            }
            try {
                String openID = jb.getString("openid");  //openid用户唯一标识
                String access_token = jb.getString("access_token");
                String expires = jb.getString("expires_in");
                if (!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openID)) {
                    mSelectedBindOpenId = openID;
                    AM.mTencent.setOpenId(openID);
                    AM.mTencent.setAccessToken(access_token, expires);
                    mPresenter.wecahtOrQQLogin("qq", openID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            ToastUtils.showShort(uiError.errorMessage);
        }

        @Override
        public void onCancel() {

        }
    }
}