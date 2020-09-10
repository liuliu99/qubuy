package com.qubuyer.business.order.model;


import com.qubuyer.base.mvp.BaseModel;
import com.qubuyer.bean.order.OrderCommentSubmitParamEntity;

import java.util.List;

public interface IOrderCommentListModel extends BaseModel {
    void submitComment(String orderId, List<OrderCommentSubmitParamEntity> goodList);
}
