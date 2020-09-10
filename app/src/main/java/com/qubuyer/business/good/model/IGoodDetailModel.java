package com.qubuyer.business.good.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IGoodDetailModel extends BaseModel {
    void getGoodDetail(String goodId);

    void getShopCartGoodList();

    void collectGood(String goodId);

    void addGoodToCart(String goodId, int count, String item_id);

    void getGoodCommentList(String goodId, int type);

    void getGoodAssess(String goodId);
}
