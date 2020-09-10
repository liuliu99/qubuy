package com.qubuyer.business.mine.model;

import android.text.TextUtils;

import com.qubuyer.bean.mine.IncomeEntity;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.presenter.IIncomeListPresenter;
import com.qubuyer.business.mine.presenter.ISaleAmountListPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class IncomeModel implements IIncomeModel {
    private IIncomeListPresenter mPresenter;
    private int pageNo = 1;

    public IncomeModel(IIncomeListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void loadAllData(int loadType, String type) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }
        params.put("page", pageNo + "");
        HttpInvoker.createBuilder(NetConstant.GET_IN_COME_LIST_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(IncomeEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadDataResult(loadType, serverResponse);
            }
        });
    }
}
