package com.qubuyer.business.register.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IForgetModel extends BaseModel {
    void getVerificationcode(String phone);
    void findPwd(String mobile, String password, String code, String password2);
}
