package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IWithdrawPresenter {
    void getWalletInfo();
    void onGetWalletInfo(ServerResponse serverResponse);

    void getUserInfo();
    void onGetUserInfo(ServerResponse serverResponse);

    void getVerificationcode(String phone);
    void onGetVerificationcode(ServerResponse serverResponse);

    void withdraw(String money, String oauth, String mobile, String code);
    void onWithdraw(ServerResponse serverResponse);
}
