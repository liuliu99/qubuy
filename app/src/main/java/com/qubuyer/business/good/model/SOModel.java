package com.qubuyer.business.good.model;

import android.text.TextUtils;

import com.qubuyer.bean.good.GoodSOResultEntity;
import com.qubuyer.bean.good.SOGoodInfoEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.business.good.presenter.SOPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class SOModel implements ISOModel {
    private SOPresenter mPresenter;

    public SOModel(SOPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getSOInfo(int type, String goodId, int count, String itemId) {
        HashMap<String, String> map = new HashMap<>();
        if (type == 1) {
            map.put("goods_num", count + "");
            map.put("goods_id", goodId);
            map.put("item_id", itemId);
        }
        HttpInvoker.createBuilder(type == 1 ? NetConstant.BUY_NOW_POST : NetConstant.SHOP_CART_BUY_NOW_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(SOGoodInfoEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetSOInfo(serverResponse);
            }
        });
    }

    @Override
    public void calcOrderPrice(int type, String address_id, String goods_id, String goods_num, String item_id) {
        HashMap<String, String> map = new HashMap<>();
        if (type == 1) {
            map.put("goods_num", goods_num + "");
            map.put("goods_id", goods_id);
            map.put("item_id", item_id);
        }
        map.put("address_id", address_id);
        HttpInvoker.createBuilder(NetConstant.CALC_ORDER_PRICE_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(CalcOrderPriceResultEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onCalcOrderPrice(serverResponse);
            }
        });
    }

    @Override
    public void submitOrder(int type, String address_id, String invoice_type, String invoice_title, String invoice_taxpayer, String invoice_name, String invoice_desc, String invoice_email, String user_note, String goods_id, String goods_num, String item_id, String invoice_mobile) {
        HashMap<String, String> map = new HashMap<>();
        map.put("address_id", address_id);
        if (!TextUtils.isEmpty(invoice_type)) {
            map.put("invoice_type", invoice_type);
        }
        if (!TextUtils.isEmpty(invoice_title)) {
            map.put("invoice_title", invoice_title);
        }
        if (!TextUtils.isEmpty(invoice_taxpayer)) {
            map.put("invoice_taxpayer", invoice_taxpayer);
        }
        if (!TextUtils.isEmpty(invoice_name)) {
            map.put("invoice_name", invoice_name);
        }
        if (!TextUtils.isEmpty(invoice_desc)) {
            map.put("invoice_desc", invoice_desc);
        }
        if (!TextUtils.isEmpty(invoice_email)) {
            map.put("invoice_email", invoice_email);
        }
        if (!TextUtils.isEmpty(user_note)) {
            map.put("user_note", user_note);
        }
        if (type == 1) {
            map.put("goods_id", goods_id);
            map.put("goods_num", goods_num);
            map.put("item_id", item_id);
        }
        map.put("invoice_mobile", invoice_mobile);
        HttpInvoker.createBuilder(type == 1 ? NetConstant.BUY_NOW_SO_POST : NetConstant.SHOP_CART_BUY_SO_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(GoodSOResultEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onSubmitOrder(serverResponse);
            }
        });
    }
}
