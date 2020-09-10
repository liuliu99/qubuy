package com.qubuyer.business.category.model;

import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.business.category.presenter.ICategoryPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class CategoryModel implements ICategoryModel {
    private ICategoryPresenter mPresenter;
    private int pageNo = 1;

    public CategoryModel(ICategoryPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void loadFirstCategory() {
        HttpInvoker.createBuilder(NetConstant.GET_GOOD_CATEGORY_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(CategoryFirstEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadFirstCategory(serverResponse);
            }
        });
    }
}
