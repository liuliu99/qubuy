package com.qubuyer.business.payment.model;

import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.bean.payment.PayResultEntity;
import com.qubuyer.bean.payment.WechatPayParamEntity;
import com.qubuyer.business.payment.presenter.ISOPayPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SOPayModel implements ISOPayModel {

    private ISOPayPresenter presenter;

    public SOPayModel(ISOPayPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void destroy() {
        presenter = null;
    }

    @Override
    public void loadPayListData() {
        List<PayListEntity> list = new ArrayList<>();
        PayListEntity entity;
        for (int i = 0; i < 2; i++) {
            entity = new PayListEntity();
            entity.setSelected(i == 0);
            entity.setType(i + 1);
            entity.setSpecial(i == 0);
            switch (i) {
                case 0:
                    entity.setTypeContent("微信支付");
                    break;
                case 1:
                    entity.setTypeContent("支付宝支付");
                    break;
                case 2:
                    entity.setTypeContent("余额支付");
                    break;
            }
            list.add(entity);
        }
        presenter.onLoadPayListData(list);
    }

    @Override
    public void getWechatPayParam(String orderNo) {
        Map<String,String> params = new HashMap<>();
        params.put("id", orderNo);
        HttpInvoker.createBuilder(NetConstant.GET_WECHAT_PAY_PARAM_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(WechatPayParamEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == presenter) return;
                presenter.onGetWechatPayParam(serverResponse);
            }
        });
    }

    @Override
    public void getAlipayParam(String orderNo) {
        Map<String,String> params = new HashMap<>();
        params.put("id", orderNo);
        HttpInvoker.createBuilder(NetConstant.GET_ALIPAY_PAY_PARAM_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == presenter) return;
                presenter.onGetAlipayParam(serverResponse);
            }
        });
    }

    @Override
    public void getOrderPayResult(String orderNo) {
        Map<String,String> params = new HashMap<>();
        params.put("id", orderNo);
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_STATUS_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(PayResultEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == presenter) return;
                presenter.onGetOrderPayResult(serverResponse);
            }
        });
    }

    @Override
    public void getUserWallet() {
//        HttpInvoker.createBuilder(NetConstant.GET_USER_WALLET_URL)
//                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_GET)
//                .setClz(UserWalletEntity.class)
//                .build().sendAsyncHttpRequest(new HttpCallback() {
//            @Override
//            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
//                presenter.onGetUserWallet(serverResponse);
//            }
//        });
    }
}
