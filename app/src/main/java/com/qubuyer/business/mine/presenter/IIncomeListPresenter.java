package com.qubuyer.business.mine.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IIncomeListPresenter {
    void loadMore(String type);
    void refresh(String type);
    void onLoadDataResult(int loadType, ServerResponse serverResponse);
}
