package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IOrderDetailModel extends BaseModel {
    void getOrderDetail(String orderId, String sid);
    void addGoodToCart(String goodId, int count, String item_id);
    void cancelOrder(String orderId, String reasonId);
    void confirmGood(String orderId);
    void getCancelReasonList();
    void extendReceiving(String orderId);
    void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type);
    void delOrder(String orderId);
}
