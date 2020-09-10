package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineCardPresenter {
    void getQrCode();
    void onGetQrCode(ServerResponse serverResponse);

    void getPoster();
    void onGetPoster(ServerResponse serverResponse);

    void getUserInfo();
    void onGetUserInfo(ServerResponse serverResponse);
}
