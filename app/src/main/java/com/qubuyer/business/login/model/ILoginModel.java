package com.qubuyer.business.login.model;

import com.qubuyer.base.mvp.BaseModel;

public interface ILoginModel extends BaseModel {
    void login(String phone, String password);
    void wecahtOrQQLogin(String oauth, String openId);
    void getVerificationcode(String phone);
    void loginWithPhoneCode(String phone, String code);
}
