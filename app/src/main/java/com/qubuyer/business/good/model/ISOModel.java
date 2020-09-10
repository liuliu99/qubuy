package com.qubuyer.business.good.model;

import com.qubuyer.base.mvp.BaseModel;

public interface ISOModel extends BaseModel {
    void getSOInfo(int type, String goodId, int count, String itemId);

    void calcOrderPrice(int type, String address_id, String goods_id, String goods_num, String item_id);

    void submitOrder(int type, String address_id, String invoice_type, String invoice_title, String invoice_taxpayer, String invoice_name, String invoice_desc, String invoice_email, String user_note, String goods_id, String goods_num, String item_id, String invoice_mobile);
}
