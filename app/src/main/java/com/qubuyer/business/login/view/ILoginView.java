package com.qubuyer.business.login.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;

public interface ILoginView extends BaseView {
    void onShowLoginResultToView(LoginEntity result);
    void showWechatOrQQLoginSuccessView(ThirdLoginEntity entity);
    void showWechatOrQQLoginNoBindView(ThirdLoginEntity result);
    void showWechatOrQQLoginToSetPwdView(ThirdLoginEntity result);
    void showWechatOrQQLoginBindFailView(ThirdLoginEntity result);
    void showWechatOrQQLoginRegisterSuccessView(ThirdLoginEntity result);
    void onShowVerificationcodeResultToView(boolean result);
}
