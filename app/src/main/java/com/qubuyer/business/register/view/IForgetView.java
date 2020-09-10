package com.qubuyer.business.register.view;


import com.qubuyer.base.mvp.BaseView;

public interface IForgetView extends BaseView {
    void onShowVerificationcodeResultToView(boolean success);
    void onShowFindPwdResultToView(boolean result);
}
