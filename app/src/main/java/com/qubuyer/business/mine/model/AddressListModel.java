package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.presenter.IAddressListPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangtianming
 * @date 2018/12/19
 * @description 地址管理Model类
 */
public class AddressListModel implements IAddressListModel {
    private IAddressListPresenter addressListPresenter;

    public AddressListModel(IAddressListPresenter addressListPresenter) {
        this.addressListPresenter = addressListPresenter;
    }

    @Override
    public void loadAddressList(String page, String page_size, HttpCallback callback) {
        HttpInvoker.createBuilder(NetConstant.ADDRESS_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineAddressEntitiy[].class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void delAddress(String addressId, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("address_id", addressId);
        HttpInvoker.createBuilder(NetConstant.ADDRESS_DEL_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void setDefAddress(String addressId, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("address_id", addressId);
        HttpInvoker.createBuilder(NetConstant.ADDRESS_DEFULT_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void destroy() {
        addressListPresenter = null;
    }
}
