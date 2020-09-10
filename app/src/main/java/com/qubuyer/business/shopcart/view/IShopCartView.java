package com.qubuyer.business.shopcart.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;

import java.util.List;

public interface IShopCartView extends BaseView {
    void onShowShopCartListDataToView(List<ShopCartGoodEntity> list);
    void onShowShopCartSpecialListDataToView(List<HomeGoodEntity> list);
    void onShowDeleteGoodResultToView(boolean result);
    void onShowCollectGoodResultToView(boolean result);
    void onShowGoodCountChangeResultToView(boolean result);
    void onShowGoodCheckedResultToView(boolean result);
    void onShowGoodUnCheckResultToView(boolean result);
    void onShowGoodAllCheckOrNotResultToView(boolean result);
    void onShowClearLoseEfficacyResultToView(boolean result);
    void onShowCalcOrderPriceResultToView(CalcOrderPriceResultEntity entity);
}
