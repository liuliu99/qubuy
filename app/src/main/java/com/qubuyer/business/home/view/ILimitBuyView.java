package com.qubuyer.business.home.view;

import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.home.HomeGoodEntity;

import java.util.List;

public interface ILimitBuyView extends BaseView {
    void onShowLimitBuyDataToView(long serverTime, List<HomeGoodEntity> list);
}
