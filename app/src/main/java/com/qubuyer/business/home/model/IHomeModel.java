package com.qubuyer.business.home.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IHomeModel extends BaseModel {
    void loadBanner();
    void loadCategory();
    void loadSaleTop();
    void loadUserInfo();
    void loadSpecialInfo(String type);
    void loadSuperReturnInfo();
    void loadDailyDiscountInfo();
    //商品类型 1:超级返 2:每日限量 3:热门推荐
    void loadGoodList(boolean isClick, int goodType, int loadType);
    void getUserMessageCount();
}
