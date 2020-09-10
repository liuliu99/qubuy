package com.qubuyer.business.order.model;

import com.qubuyer.bean.order.OrderLogisticsEntity;
import com.qubuyer.business.order.presenter.IOrderLogisticsPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class OrderLogisticsModel implements IOrderLogisticsModel {
    private IOrderLogisticsPresenter mPresenter;
    private int pageNo = 1;

    public OrderLogisticsModel(IOrderLogisticsPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getLogisticsList(String id) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", id);
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_LOGISTICS_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(param)
                .setClz(OrderLogisticsEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetLogisticsList(serverResponse);
            }
        });
    }
}
