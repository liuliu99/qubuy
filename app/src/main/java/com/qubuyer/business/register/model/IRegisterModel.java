package com.qubuyer.business.register.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IRegisterModel extends BaseModel {
    void getVerificationcode(String phone);
    void register(String username, String password, String mobile_code, String invitation_code);
}
