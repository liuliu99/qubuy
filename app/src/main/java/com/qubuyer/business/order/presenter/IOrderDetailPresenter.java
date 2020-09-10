package com.qubuyer.business.order.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IOrderDetailPresenter {
    void getOrderDetail(String orderId, String sid);
    void onGetOrderDetail(ServerResponse serverResponse);

    void addGoodToCart(String goodId, int count, String item_id);
    void onAddGoodToCart(ServerResponse serverResponse);

    void cancelOrder(String orderId, String cancelId);
    void onCancelOrder(ServerResponse serverResponse);

    void delOrder(String orderId);
    void onDelOrder(ServerResponse serverResponse);

    void confirmGood(String orderId);
    void onConfirmGood(ServerResponse serverResponse);

    void getCancelReasonList();
    void onGetCancelReasonList(ServerResponse serverResponse);

    void extendReceiving(String orderId);
    void onExtendReceiving(ServerResponse serverResponse);

    void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type);
    void onApplyInvoice(ServerResponse serverResponse);
}
