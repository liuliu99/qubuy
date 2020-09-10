package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.presenter.IRebateOrderPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class RebateOrderModel implements IRebateOrderModel {
    private IRebateOrderPresenter mPresenter;
    private int pageNo = 1;

    public RebateOrderModel(IRebateOrderPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }


    @Override
    public void loadAllData(int loadType, int type) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("page", pageNo + "");
        HttpInvoker.createBuilder(NetConstant.GET_REBATE_ORDER_LIST_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(RebateOrderEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadDataResult(loadType, serverResponse);
            }
        });
    }
}
