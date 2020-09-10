package com.qubuyer.business.order.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IOrderLogisticsPresenter {
    void getLogisticsList(String id);
    void onGetLogisticsList(ServerResponse serverResponse);
}
