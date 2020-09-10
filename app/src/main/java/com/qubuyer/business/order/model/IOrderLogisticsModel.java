package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IOrderLogisticsModel extends BaseModel {
    void getLogisticsList(String id);
}
