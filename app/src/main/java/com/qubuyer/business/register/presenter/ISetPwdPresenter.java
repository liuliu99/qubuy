package com.qubuyer.business.register.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ISetPwdPresenter {
    void setPwd(String token, String pushId, String password, String password2);
    void onSetPwd(ServerResponse serverResponse);
}
