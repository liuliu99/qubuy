package com.qubuyer.business.register.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.register.RegisterEntity;

public interface IRegisterView extends BaseView {
    void onShowVerificationcodeResultToView(boolean success);
    void onShowResiterResultToView(RegisterEntity result);
}
