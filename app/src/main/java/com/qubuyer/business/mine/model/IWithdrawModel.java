package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IWithdrawModel extends BaseModel {
    void getWalletInfo();
    void getUserInfo();
    void getVerificationcode(String phone);
    void withdraw(String money, String oauth, String mobile, String code);
}
