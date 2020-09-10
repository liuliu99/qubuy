package com.qubuyer.business.order.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundEntity;

import java.util.List;

public interface IOrderRefundListView extends BaseRefreshView {
    void onShowRefreshListToView(List<OrderRefundEntity> list);
    void onShowLoadMoreListToView(List<OrderRefundEntity> list);
    void onShowRefundDetailToView(OrderRefundEntity entity);
    void onShowCancelRefundResultToView(boolean result);
    void destory();
}
