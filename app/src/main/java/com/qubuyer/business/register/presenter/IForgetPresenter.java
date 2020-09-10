package com.qubuyer.business.register.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IForgetPresenter {
    void getVerificationcode(String phone);
    void onGetVerificationcode(ServerResponse serverResponse);

    void findPwd(String mobile, String password, String code, String password2);
    void onFindPwd(ServerResponse serverResponse);
}
