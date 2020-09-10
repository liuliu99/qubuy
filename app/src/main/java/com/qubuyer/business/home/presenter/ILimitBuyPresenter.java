package com.qubuyer.business.home.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface ILimitBuyPresenter {
    void loadLimitBuyData();
    void onLoadLimitBuyData(ServerResponse serverResponse);
}
