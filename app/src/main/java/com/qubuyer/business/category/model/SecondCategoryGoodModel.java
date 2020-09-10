package com.qubuyer.business.category.model;

import com.qubuyer.bean.category.SecondCategoryGoodEntity;
import com.qubuyer.business.category.presenter.ISecondCategoryGoodPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class SecondCategoryGoodModel implements ISecondCategoryGoodModel {
    private ISecondCategoryGoodPresenter mPresenter;
    private int pageNo = 1;

    public SecondCategoryGoodModel(ISecondCategoryGoodPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void loadData(int loadType, String firstCategoryId, String secondCategoryId) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        HashMap<String,String> params = new HashMap<>();
        params.put("page", pageNo + "");
        params.put("pid", firstCategoryId);
        params.put("cid", secondCategoryId);
        HttpInvoker.createBuilder(NetConstant.GET_SECOND_CATEGORY_GOOD_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(params)
                .setClz(SecondCategoryGoodEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadData(loadType, serverResponse);
            }
        });
    }
}
