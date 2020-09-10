package com.qubuyer.business.order.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;

import java.util.List;

public interface IOrderDetailView extends BaseView {
    void onShowOrderDetailToView(OrderEntity entity);
    void onShowAddGoodToCartResultToView(boolean result);
    void onShowCancelOrderResultToView(boolean result);
    void onShowDelOrderResultToView(boolean result);
    void onShowConfirmGoodResultToView(boolean result);
    void onShowCancelReasonListToView(List<OrderRefundReasonEntity> list);
    void onShowExtendReceivingResultToView(boolean result);
    void onShowApplyInvoiceResultToView(boolean result);
    void destory();
}
