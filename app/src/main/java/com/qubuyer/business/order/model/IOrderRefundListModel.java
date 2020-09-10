package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IOrderRefundListModel extends BaseModel {
    void getRefundList(int loadType);
    void getRefundDetail(String id);
    void cancelRefund(String id);
}
