package com.qubuyer.business.mine.model;

import com.qubuyer.bean.address.ProvinceData;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.presenter.IAddressInfoPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangtianming
 * @date 2018/12/19
 * @description 地址操作Model类
 */
public class AddressInfoModel implements IAddressInfoModel {

    private IAddressInfoPresenter addressInfoPresenter;

    public AddressInfoModel(IAddressInfoPresenter addressInfoPresenter) {
        this.addressInfoPresenter = addressInfoPresenter;
    }

    @Override
    public void addNewAddress(MineAddressEntitiy info, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("consignee", info.getConsignee());
        params.put("province", info.getProvince() + "");
        params.put("city", info.getCity() + "");
        params.put("district", info.getDistrict() + "");
//        params.put("twon", "萨法撒旦法");
        params.put("address", info.getAddress());
        params.put("mobile", info.getMobile());
        params.put("is_default", info.getIs_default() + "");
//        params.put("address_id", info.getAddress_id());

        HttpInvoker.createBuilder(NetConstant.ADDRESS_ADD_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void updateAddress(MineAddressEntitiy info, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("consignee", info.getConsignee());
        params.put("province", info.getProvince() + "");
        params.put("city", info.getCity() + "");
        params.put("district", info.getDistrict() + "");
//        params.put("twon", info.getDistrict()+"");
        params.put("address", info.getAddress());
        params.put("mobile", info.getMobile());
        params.put("is_default", info.getIs_default() + "");
        params.put("address_id", info.getAddress_id());

        HttpInvoker.createBuilder(NetConstant.ADDRESS_ADD_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
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
    public void getAddressInfo(String addressId, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", addressId);

        HttpInvoker.createBuilder(NetConstant.ADDRESS_INFO_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_GET)
                .setClz(MineAddressEntitiy.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void getAddressSelectList(HttpCallback callback) {
        HttpInvoker.createBuilder(NetConstant.ADDRESS_SELECT_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_GET)
                .setClz(ProvinceData[].class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void destroy() {
        addressInfoPresenter = null;
    }
}
