package com.qubuyer.business.mine.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IRebateOrderDetailModel extends BaseModel {
    void getReabteOrderDetail(String id);
    void rebateClose(String id);
}
