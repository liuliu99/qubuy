package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineLotteryRecordEntity;
import com.qubuyer.business.mine.presenter.IMineLotteryRecordPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class MineLotteryRecordModel implements IMineLotteryRecordModel {
    private IMineLotteryRecordPresenter mPresenter;

    public MineLotteryRecordModel(IMineLotteryRecordPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getMineLotteryRecordList() {
        HttpInvoker.createBuilder(NetConstant.GET_MINE_LOTTERYRECORD_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineLotteryRecordEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetMineLotteryRecordList(serverResponse);
            }
        });
    }

    @Override
    public void getLottery(String id, String consignee, String mobile, String province, String city, String district, String address) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("consignee", consignee);
        map.put("mobile", mobile);
        map.put("province", province);
        map.put("city", city);
        map.put("district", district);
        map.put("address", address);
        HttpInvoker.createBuilder(NetConstant.GET_MINE_LOTTERY_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .setParams(map)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetLottery(serverResponse);
            }
        });
    }
}
