package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IWalletPresenter {
    void getWalletInfo();
    void onGetWalletInfo(ServerResponse serverResponse);
}
