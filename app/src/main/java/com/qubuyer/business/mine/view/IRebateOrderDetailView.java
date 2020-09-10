package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.RebateOrderEntity;

public interface IRebateOrderDetailView extends BaseView {
    void onShowRebateOrderDetailToView(RebateOrderEntity entity);
    void onShowRebateCloseResultToView(boolean result);
}
