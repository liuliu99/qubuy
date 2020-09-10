package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.bean.mine.SaleAmountEntity;

import java.util.List;

public interface IRebateOrderPageView extends BaseRefreshView {
    void onShowRefreshListToView(List<RebateOrderEntity> list);
    void onShowLoadMoreListToView(List<RebateOrderEntity> list);
    void destory();
}
