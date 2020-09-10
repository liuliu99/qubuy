package com.qubuyer.business.home.model;

import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.presenter.IHomeSecondGoodListPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class HomeSecondGoodListModel implements IHomeSecondGoodListModel {
    private IHomeSecondGoodListPresenter mPresenter;
    private int pageNo = 1;

    public HomeSecondGoodListModel(IHomeSecondGoodListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getGoodList(int loadType, int type) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", pageNo + "");
        String url = null;
        switch (type) {
            case 1:
                url = NetConstant.GET_HOME_SECOND_GOOD_LIST_POST;
                params.put("type", "fine");
                break;
            case 2:
                url = NetConstant.GET_HOME_LIMITED_BUY_POST;
                break;
            case 3:
                url = NetConstant.GET_HOME_SECOND_GOOD_LIST_POST;
                params.put("type", "hot");
                break;
            case 4:
                url = NetConstant.GET_HOME_SECOND_GOOD_LIST_POST;
                params.put("type", "new");
                break;
            case 5:
                url = NetConstant.GET_HOME_SUPER_RETURN_POST;
                break;
            case 6:
                url = NetConstant.GET_HOME_DAILYDISCOUNT_POST;
                break;
        }
        HttpInvoker.createBuilder(url)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetGoodList(loadType, serverResponse);
            }
        });
    }
}
