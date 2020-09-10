package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.presenter.IRebateOrderDetailPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class RebateOrderDetailModel implements IRebateOrderDetailModel {
    private IRebateOrderDetailPresenter mPresenter;
    private int pageNo = 1;

    public RebateOrderDetailModel(IRebateOrderDetailPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getReabteOrderDetail(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        HttpInvoker.createBuilder(NetConstant.GET_REBATE_ORDER_DETAIL_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(RebateOrderEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetRebateOrderDetail(serverResponse);
            }
        });
    }

    @Override
    public void rebateClose(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        HttpInvoker.createBuilder(NetConstant.REBATE_CLOSE_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onReabteClose(serverResponse);
            }
        });
    }
}
