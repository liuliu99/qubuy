package com.qubuyer.business.home.model;

import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.presenter.ILimitBuyPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class LimitBuyModel implements ILimitBuyModel {
    private ILimitBuyPresenter mPresenter;
    private int pageNo = 1;

    public LimitBuyModel(ILimitBuyPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void loadLimitBuyData() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_FLASH_SALE_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadLimitBuyData(serverResponse);
            }
        });
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }
}
