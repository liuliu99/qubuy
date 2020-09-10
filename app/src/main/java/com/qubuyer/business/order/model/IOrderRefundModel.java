package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;

import java.util.List;

public interface IOrderRefundModel extends BaseModel {
    void getRefundReasonList();
    void submitRefund(String rec_id, String ids, String money, String note, List<String> imgs);
}
