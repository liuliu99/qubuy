package com.qubuyer.business.mine.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IRebateOrderModel extends BaseModel {
    /**
     * 获取折让列表
     * @param loadType
     * @param type 类型 1:已结算 2:未结算
     */
    void loadAllData(final int loadType, int type);
}
