package com.qubuyer.business.mine.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IRebateOrderDetailPresenter {
    void getReabteOrderDetail(String id);
    void onGetRebateOrderDetail(ServerResponse serverResponse);

    void rebateClose(String id);
    void onReabteClose(ServerResponse serverResponse);
}
