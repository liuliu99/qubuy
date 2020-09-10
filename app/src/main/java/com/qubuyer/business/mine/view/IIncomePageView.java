package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.IncomeEntity;
import com.qubuyer.bean.mine.SaleAmountEntity;

import java.util.List;

public interface IIncomePageView extends BaseRefreshView {
    void onShowRefreshListToView(List<IncomeEntity> list);
    void onShowLoadMoreListToView(List<IncomeEntity> list);
    void destory();
}
