package com.qubuyer.business.order.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IOrderRefundListPresenter {
    void loadMore();
    void refresh();
    void onLoadListResult(int loadType, ServerResponse serverResponse);

    void getRefundDetail(String id);
    void onGetRefundDetail(ServerResponse serverResponse);

    void cancelRefund(String id);
    void onCancelRefund(ServerResponse serverResponse);
}
