package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineFansEntity;
import com.qubuyer.business.mine.presenter.MineFansListPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class MineFansListModel implements IMineFansListModel {
    private MineFansListPresenter mPresenter;

    public MineFansListModel(MineFansListPresenter minePresenter) {
        this.mPresenter = minePresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void loadFansListData() {
        HttpInvoker.createBuilder(NetConstant.GET_MINE_FANS_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineFansEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadFansListDataSuccess(serverResponse);
            }
        });
    }
}
