package com.qubuyer.business.register.model;

import com.qubuyer.base.mvp.BaseModel;

public interface ISetPwdModel extends BaseModel {
    void setPwd(String token, String pushId, String password, String password2);
}
