package com.qubuyer.business.home.view;

import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;

import java.util.List;

public interface IHomeView extends BaseRefreshView {
    void onShowBannerToView(List<HomeBannerEntity> list);

    void onShowCategoryToView(List<HomeCategoryEntity> list);

    void onShowSaleTopToView(HomeSaleTopEntity entity);
    void onShowUserInfoToView(UserEntity entity);
    void onShowChoicenessGoodListToView(List<HomeGoodEntity> list);
    void onShowLimitGoodListToView(List<HomeGoodEntity> list);
    void onShowSpecialGoodListToView(List<HomeGoodEntity> list);
    void onShowFirstpublishGoodListToView(List<HomeGoodEntity> list);
    void onShowSuperReturnGoodListToView(List<HomeGoodEntity> list);
    void onShowDailyDiscountGoodListToView(List<HomeGoodEntity> list);

    void onShowRefreshDataToView(List<HomeGoodEntity> list);
    void onShowClickDataToView(List<HomeGoodEntity> list);

    void onShowLoadMoreGoodDataToView(List<HomeGoodEntity> list);

    void onShowUserMessageCountDataToView(UserMessageCountEntity entity);
}
