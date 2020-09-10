package com.qubuyer.business.mine.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineFansListPresenter {
    void loadFansListData();
    void onLoadFansListDataSuccess(ServerResponse serverResponse);
}
