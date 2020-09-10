package com.qubuyer.business.order.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;

import java.util.List;

public interface IOrderRefundView extends BaseView {
    void onShowRefundReasonListToView(List<OrderRefundReasonEntity> list);
    void onShowSubmitRefundResultToView(boolean result);
    void destory();
}
