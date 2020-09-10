package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMinePresenter {
    void getUserInfo();
    void onGetUserInfo(ServerResponse serverResponse);

    void getUserMessageCount();
    void onGetUserMessageCount(ServerResponse serverResponse);

    void loginOut();
    void onLoginOut(ServerResponse serverResponse);

    void getWalletInfo();
    void onGetWalletInfo(ServerResponse serverResponse);
}
