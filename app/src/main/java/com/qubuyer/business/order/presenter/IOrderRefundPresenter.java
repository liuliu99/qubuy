package com.qubuyer.business.order.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.List;

public interface IOrderRefundPresenter {
    void getRefundReasonList();
    void onGetRefundReasonList(ServerResponse serverResponse);

    void submitRefund(String rec_id, String ids, String money, String note, List<String> imgs);
    void onSubmitRefund(ServerResponse serverResponse);
}
