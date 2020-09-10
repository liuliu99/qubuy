package com.qubuyer.business.mine.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface ISaleAmountListPresenter {

    void loadMore(int type);

    void refresh(int type);

    void onLoadDataResult(int loadType, ServerResponse serverResponse);
}
