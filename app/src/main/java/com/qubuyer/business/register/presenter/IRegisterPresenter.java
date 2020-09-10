package com.qubuyer.business.register.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IRegisterPresenter {
    void getVerificationcode(String phone);
    void onGetVerificationcode(ServerResponse serverResponse);

    void register(String username, String password, String mobile_code, String invitation_code);
    void onRegister(ServerResponse serverResponse);
}
