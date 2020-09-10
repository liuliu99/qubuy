package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineBrowseFootprintEntity;
import com.qubuyer.business.mine.presenter.IMineBrowseFootprintPresenter;
import com.qubuyer.business.mine.presenter.IMineCollectionListPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class MineBrowseFootprintListModel implements IMineBrowseFootprintListModel {
    private IMineBrowseFootprintPresenter mMinePresenter;

    public MineBrowseFootprintListModel(IMineBrowseFootprintPresenter minePresenter) {
        this.mMinePresenter = minePresenter;
    }

    @Override
    public void destroy() {
        mMinePresenter = null;
    }

    @Override
    public void loadBrowseFootprintListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "android");
        HttpInvoker.createBuilder(NetConstant.GET_MINE_BROWSEFOOTPRINT_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineBrowseFootprintEntity[].class)
                .setParams(map)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onLoadBrowseFootprintListData(serverResponse);
            }
        });
    }

    @Override
    public void deleteBrowseFootprint(String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpInvoker.createBuilder(NetConstant.GET_MINE_DELETE_BROWSEFOOTPRINT_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .setParams(map)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onDeleteBrowseFootprint(serverResponse);
            }
        });
    }

    @Override
    public void clearBrowseFootprint() {
        HttpInvoker.createBuilder(NetConstant.GET_MINE_CLEAR_BROWSEFOOTPRINT_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onClearBrowseFootprint(serverResponse);
            }
        });
    }
}
