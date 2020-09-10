package com.qubuyer.business.shopcart.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IShopCartModel extends BaseModel {
    void getShopCartGoodList();
    void getShopCartSpecialGoodList();
    void deleteGood(String shopCartId);
    void collecGood(String goodsId);
    void goodCountChange(String shopCartId, int count);
    void goodChecked(String shopCartId);
    void goodUnCheck(String shopCartId);
    //是否全选 1:全选 2:全选取消
    void goodAllCheckOrNot(int type);
    void clearLoseEfficacyGood(String ids);
    void calcOrderPrice();
}
