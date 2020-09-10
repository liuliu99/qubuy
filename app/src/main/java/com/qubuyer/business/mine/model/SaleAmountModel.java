package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.presenter.ISaleAmountListPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class SaleAmountModel implements ISaleAmountModel {
    private ISaleAmountListPresenter mPresenter;
    private int pageNo = 1;

    public SaleAmountModel(ISaleAmountListPresenter mPresenter) {
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
        HttpInvoker.createBuilder(NetConstant.GET_SALE_AMOUNT_LIST_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(SaleAmountEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadDataResult(loadType, serverResponse);
            }
        });
    }
}
