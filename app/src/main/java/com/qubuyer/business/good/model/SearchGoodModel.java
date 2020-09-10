package com.qubuyer.business.good.model;

import android.text.TextUtils;

import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.good.presenter.ISearchGoodPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class SearchGoodModel implements ISearchGoodModel {
    private ISearchGoodPresenter mPresenter;
    private int pageResultSecondNo = 1;

    public SearchGoodModel(ISearchGoodPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void loadSearchResultFirstList(String key) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(key)) {
            params.put("value", key);
        }
        HttpInvoker.createBuilder(NetConstant.SEARCH_GOOD_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadSearchResultFirstListByKey(serverResponse);
            }
        });
    }
}
