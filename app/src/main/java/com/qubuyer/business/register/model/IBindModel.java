package com.qubuyer.business.register.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IBindModel extends BaseModel {
    void getVerificationcode(String phone);
    void bindPhone(String mobile, String code, String openid, String oauth);
}
