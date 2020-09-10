package com.qubuyer.business.order.model;

import android.text.TextUtils;

import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.presenter.IOrderDetailPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailModel implements IOrderDetailModel {
    private IOrderDetailPresenter mPresenter;
    private int pageNo = 1;

    public OrderDetailModel(IOrderDetailPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getOrderDetail(String orderId, String sid) {
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        if (!TextUtils.isEmpty(sid)) {
            params.put("sid", sid);
        }
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_DETAIL_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(OrderEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetOrderDetail(serverResponse);
            }
        });
    }

    @Override
    public void addGoodToCart(String goodId, int count, String item_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodId);
        map.put("goods_num", count + "");
        map.put("item_id", item_id + "");
        HttpInvoker.createBuilder(NetConstant.ADD_GOOD_TO_CART_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onAddGoodToCart(serverResponse);
            }
        });
    }

    @Override
    public void cancelOrder(String orderId, String reasonId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        if (!TextUtils.isEmpty(reasonId)) {
            params.put("ids[0]", reasonId);
        }
        HttpInvoker.createBuilder(NetConstant.CANCEL_ORDER_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onCancelOrder(serverResponse);
            }
        });
    }

    @Override
    public void confirmGood(String orderId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        HttpInvoker.createBuilder(NetConstant.ORDER_CONFIRM_GOOD_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onConfirmGood(serverResponse);
            }
        });
    }

    @Override
    public void getCancelReasonList() {
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_REASON_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(OrderRefundReasonEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetCancelReasonList(serverResponse);
            }
        });
    }

    @Override
    public void extendReceiving(String orderId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        HttpInvoker.createBuilder(NetConstant.ORDER_EXTEND_RECEIVING_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onExtendReceiving(serverResponse);
            }
        });
    }

    @Override
    public void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        if (!TextUtils.isEmpty(sid)) {
            params.put("sid", sid);
        }
        if (!TextUtils.isEmpty(invoice_taxpayer)) {
            params.put("invoice_taxpayer", invoice_taxpayer);
        }
        if (!TextUtils.isEmpty(invoice_mobile)) {
            params.put("invoice_mobile", invoice_mobile);
        }
        if (!TextUtils.isEmpty(invoice_email)) {
            params.put("invoice_email", invoice_email);
        }
        if (!TextUtils.isEmpty(invoice_title)) {
            params.put("invoice_title", invoice_title);
        }
        if (!TextUtils.isEmpty(invoice_name)) {
            params.put("invoice_name", invoice_name);
        }
        if (!TextUtils.isEmpty(invoice_desc)) {
            params.put("invoice_desc", invoice_desc);
        }
        if (!TextUtils.isEmpty(invoice_type)) {
            params.put("invoice_type", invoice_type);
        }
        HttpInvoker.createBuilder(NetConstant.ORDER_APPLY_INVOICE_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onApplyInvoice(serverResponse);
            }
        });
    }

    @Override
    public void delOrder(String orderId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        HttpInvoker.createBuilder(NetConstant.DEL_ORDER_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onDelOrder(serverResponse);
            }
        });
    }
}
