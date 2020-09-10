package com.qubuyer.business.order.model;

import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.order.presenter.IOrderRefundListPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class OrderRefundListModel implements IOrderRefundListModel {
    private IOrderRefundListPresenter mPresenter;
    private int pageNo = 1;

    public OrderRefundListModel(IOrderRefundListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }


    @Override
    public void getRefundList(int loadType) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", pageNo + "");
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_LIST_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(OrderRefundEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadListResult(loadType, serverResponse);
            }
        });
    }

    @Override
    public void getRefundDetail(String id) {
        HashMap<String,String> map = new HashMap<>();
        map.put("id", id);
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_DETAIL_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(OrderRefundEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetRefundDetail(serverResponse);
            }
        });
    }

    @Override
    public void cancelRefund(String id) {
        HashMap<String,String> map = new HashMap<>();
        map.put("id", id);
        HttpInvoker.createBuilder(NetConstant.CANCEL_REFUND_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onCancelRefund(serverResponse);
            }
        });
    }
}
