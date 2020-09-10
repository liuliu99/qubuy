package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;
import com.qubyer.okhttputil.callback.HttpCallback;

public interface IAddressListModel extends BaseModel {
    void loadAddressList(String page, String page_size, HttpCallback callback);
    void delAddress(String addressId, HttpCallback callback);
    void setDefAddress(String addressId, HttpCallback callback);
}
