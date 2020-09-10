package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

import java.io.File;

public interface IMineSettingPresenter {
    void loginOut();
    void onLoginOut(ServerResponse serverResponse);

    void updateHeadImg(File file);
    void onUpdateHeadImg(ServerResponse serverResponse);

    void getUserInfo();
    void onGetUserInfo(ServerResponse serverResponse);
}
