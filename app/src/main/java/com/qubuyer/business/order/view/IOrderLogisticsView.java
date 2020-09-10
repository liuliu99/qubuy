package com.qubuyer.business.order.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.order.OrderLogisticsEntity;

public interface IOrderLogisticsView extends BaseView {
    void onShowLogisticsListToView(OrderLogisticsEntity entity);
}
