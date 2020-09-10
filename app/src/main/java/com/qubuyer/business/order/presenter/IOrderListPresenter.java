package com.qubuyer.business.order.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IOrderListPresenter {

    void loadMore(String orderStatus);

    void refresh(String orderStatus);

    void onLoadOrderListSuccess(int loadType, ServerResponse serverResponse);

    void cancelOrder(String orderId, String reasonId);
    void onCancelOrder(ServerResponse serverResponse);

    void delOrder(String orderId);
    void onDelOrder(ServerResponse serverResponse);

    void getCancelReasonList();
    void onGetCancelReasonList(ServerResponse serverResponse);

    void confirmGood(String orderId);
    void onConfirmGood(ServerResponse serverResponse);

    void extendReceiving(String orderId);
    void onExtendReceiving(ServerResponse serverResponse);

    void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type);
    void onApplyInvoice(ServerResponse serverResponse);
}
