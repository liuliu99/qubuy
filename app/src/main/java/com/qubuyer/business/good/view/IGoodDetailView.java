package com.qubuyer.business.good.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;

import java.util.List;

public interface IGoodDetailView extends BaseView {
    void onShowGoodDetailToView(HomeGoodEntity entity);
    void onShowShopCartListDataToView(List<ShopCartGoodEntity> list);
    void onShowCollectGoodResultToView(boolean result);
    void onShowAddGoodToCartResultToView(boolean result);
    void onShowGoodCommentListToView(List<GoodCommentEntity> list);
    void onShowGoodAssessToView(GoodAssessEntity entity);
    void destory();
}
