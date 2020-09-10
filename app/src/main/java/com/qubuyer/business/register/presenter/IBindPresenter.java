package com.qubuyer.business.register.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IBindPresenter {
    void getVerificationcode(String phone);
    void onGetVerificationcode(ServerResponse serverResponse);

    void bindPhone(String mobile, String code, String openid, String oauth);
    void onBindPhone(ServerResponse serverResponse);
}
