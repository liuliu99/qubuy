package com.qubuyer.business.good.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ISOPresenter {
    void getSOInfo(int type, String goodId, int count, String itemId);

    void onGetSOInfo(ServerResponse serverResponse);

    void submitOrder(int type, String address_id, String invoice_type, String invoice_title, String invoice_taxpayer, String invoice_name, String invoice_desc, String invoice_email, String user_note, String goods_id, String goods_num, String item_id, String invoice_mobile);

    void onSubmitOrder(ServerResponse serverResponse);
}
