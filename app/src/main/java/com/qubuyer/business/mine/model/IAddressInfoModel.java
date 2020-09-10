package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubyer.okhttputil.callback.HttpCallback;

public interface IAddressInfoModel extends BaseModel {
    void addNewAddress(MineAddressEntitiy info, HttpCallback callback);

    void updateAddress(MineAddressEntitiy info, HttpCallback callback);

    void delAddress(String addressId, HttpCallback callback);

    void getAddressInfo(String addressId, HttpCallback callback);

    void getAddressSelectList(HttpCallback callback);
}
