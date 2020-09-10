package com.qubuyer.business.mine.presenter;


import com.qubuyer.bean.mine.MineAddressEntitiy;

public interface IAddressInfoPresenter {
    void requestNewAddress(MineAddressEntitiy info);
    void requestUpdateAddress(MineAddressEntitiy info);
    void delAddress(String addressId);
    void getAddressInfo(String addressId);

    void getAddressSelectList();
}
