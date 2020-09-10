package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.bean.order.OrderEntity;

import java.util.List;

public interface ISaleAmountPageView extends BaseRefreshView {
    void onShowRefreshListToView(SaleAmountEntity entity);
    void onShowLoadMoreListToView(SaleAmountEntity entity);
    void destory();
}
