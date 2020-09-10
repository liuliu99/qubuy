package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IMineModel extends BaseModel {
    void getUserInfo();
    void getUserMessageCount();
    void loginOut();
    void getWalletInfo();
}
