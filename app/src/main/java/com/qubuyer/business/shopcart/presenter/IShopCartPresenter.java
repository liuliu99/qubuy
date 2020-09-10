package com.qubuyer.business.shopcart.presenter;

import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.List;

public interface IShopCartPresenter {
    void getShopCartGoodList();
    void onGetShopCartGoodList(ServerResponse serverResponse);

    void getShopCartSpecialGoodList();
    void onGetShopCartSpecialGoodList(ServerResponse serverResponse);

    void deleteGood(String shopCartId);
    void onDeleteGood(ServerResponse serverResponse);

    void collecGood(String goodsId);
    void onCollectGood(ServerResponse serverResponse);

    void goodCountChange(String shopCartId, int count);
    void onGoodCountChange(ServerResponse serverResponse);

    void goodChecked(String shopCartId);
    void onGoodChecked(ServerResponse serverResponse);

    void goodUnCheck(String shopCartId);
    void onGoodUnCheck(ServerResponse serverResponse);

    void goodAllCheckOrNot(int type);
    void onGoodAllCheckOrNot(ServerResponse serverResponse);

    void clearLoseEfficacyGood(List<ShopCartGoodEntity> list);
    void onClearLoseEfficacyGood(ServerResponse serverResponse);

    void calcOrderPrice();
    void onCalcOrderPrice(ServerResponse serverResponse);
}
