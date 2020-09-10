package com.qubuyer.business.mine.presenter;

public interface IAddressListPresenter {
    void requestAdressList(String page, String page_size);
    void delAddress(String addressId);
    void setDefAddress(String addressId);
}
