package com.qubuyer.business.login.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ILoginPresenter {
    void login(String phone, String password);
    void loginWithPhoneCode(String phone, String code);
    void onLogin(ServerResponse serverResponse);

    void wecahtOrQQLogin(String oauth, String openId);
    void onWechatOrQQLogin(ServerResponse serverResponse);

    void getVerificationcode(String phone);
    void onGetVerificationcode(ServerResponse serverResponse);
}
