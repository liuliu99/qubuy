package com.qubuyer.business.good.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IGoodDetailPresenter {
    void getGoodDetail(String goodId);
    void onGetGoodDetail(ServerResponse serverResponse);

    void getShopCartGoodList();
    void onGetShopCartGoodList(ServerResponse serverResponse);

    void collectGood(String goodId);
    void onCollectGood(ServerResponse serverResponse);

    void addGoodToCart(String goodId, int count, String item_id);
    void onAddGoodToCart(ServerResponse serverResponse);

    void getGoodCommentList(String goodId, int type);
    void onGetGoodCommmentList(ServerResponse serverResponse);

    void getGoodAssess(String goodId);
    void onGetGoodAssess(ServerResponse serverResponse);
}
