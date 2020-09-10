package com.qubuyer.business.order.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;

import java.util.List;

public interface IOrderListPageView extends BaseRefreshView {
    void onShowRefreshListToView(List<OrderEntity> list);
    void onShowLoadMoreListToView(List<OrderEntity> list);
    void onShowCancelOrderResultToView(boolean result);
    void onShowDelOrderResultToView(boolean result);
    void onShowCancelReasonListToView(List<OrderRefundReasonEntity> list);
    void onShowConfirmGoodResultToView(boolean result);
    void onShowExtendReceivingResultToView(boolean result);
    void onShowApplyInvoiceResultToView(boolean result);
    void destory();
}
