package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IOrderListModel extends BaseModel {
    /**
     * 获取订单列表数据
     * @param loadType
     * @param orderStatus 分组状态 -2=退款 -1=已取消 1=待付款 2=待使用 3=待评价 4=已完成
     */
    void loadAllData(final int loadType, String orderStatus);

    void getCancelReasonList();
    /**
     * 取消订单
     * @param orderId
     */
    void cancelOrder(String orderId, String reasonId);

    void confirmGood(String orderId);

    void extendReceiving(String orderId);

    void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type);

    void delOrder(String orderId);
}
