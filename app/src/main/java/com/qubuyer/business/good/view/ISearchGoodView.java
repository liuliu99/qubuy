package com.qubuyer.business.good.view;

import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.home.HomeGoodEntity;

import java.util.List;

public interface ISearchGoodView extends BaseView {
    void onShowSearchHistoryToView(List<HomeGoodEntity> list);

    void onShowSearchResultFirstListToView(List<HomeGoodEntity> list);
}
