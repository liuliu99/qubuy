package com.qubuyer.business.register.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.register.RegisterEntity;

public interface IBindView extends BaseView {
    void onShowVerificationcodeResultToView(boolean result);
    void onShowBindPhoneSetPwdToView(RegisterEntity entity);
    void onShowBindPhoneResultToView(RegisterEntity entity);
}
