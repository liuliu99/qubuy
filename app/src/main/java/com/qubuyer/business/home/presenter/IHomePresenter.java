package com.qubuyer.business.home.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public interface IHomePresenter {
    void loadBanner();
    void onLoadBannerSuccess(ServerResponse serverResponse);

    void loadCategory();
    void onLoadCategorySuccess(ServerResponse serverResponse);

    void loadSaleTop();
    void onLoadSaleTop(ServerResponse serverResponse);

    void loadUserInfo();
    void onLoadUserInfo(ServerResponse serverResponse);

    void loadSpecialChoicenessGoodInfo();
    void onLoadSpecialChoicenessGoodInfo(ServerResponse serverResponse);

    void loadSpecialLimitGoodInfo();
    void onLoadSpecialLimitGoodInfo(ServerResponse serverResponse);

    void loadSpecialSpecialGoodInfo();
    void onLoadSpecialSpecialGoodInfo(ServerResponse serverResponse);

    void loadSpecialFirstpublishGoodInfo();
    void onLoadSpecialFirstpublishGoodInfo(ServerResponse serverResponse);

    void loadSuperReturnGoodInfo();
    void onLoadSuperReturnGoodInfo(ServerResponse serverResponse);

    void loadDailyDiscountGoodInfo();
    void onLoadDailyDiscountGoodInfo(ServerResponse serverResponse);

    void refresh(int goodType);
    void loadGoodList(int goodType);
    void onLoadGoodsSuccess(Map<String, String> requestParams,ServerResponse serverResponse, int loadType);

    void getUserMessageCount();
    void onGetUserMessageCount(ServerResponse serverResponse);
}
