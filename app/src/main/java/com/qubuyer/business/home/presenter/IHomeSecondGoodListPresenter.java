package com.qubuyer.business.home.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IHomeSecondGoodListPresenter {
    void refresh(int type);
    void loadMore(int type);
    void onGetGoodList(int loadType, ServerResponse serverResponse);
}
