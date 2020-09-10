package com.qubuyer.business.home.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.home.HomeGoodEntity;

import java.util.List;

public interface IHomeSecondGoodListPageView extends BaseRefreshView {
    void onShowRefreshListToView(List<HomeGoodEntity> list);
    void onShowLoadMoreListToView(List<HomeGoodEntity> list);
}
