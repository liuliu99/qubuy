package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineCollectEntity;
import com.qubuyer.business.mine.presenter.IMineCollectionListPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class MineCollectionListModel implements IMineCollectionListModel {
    private IMineCollectionListPresenter mMinePresenter;

    public MineCollectionListModel(IMineCollectionListPresenter minePresenter) {
        this.mMinePresenter = minePresenter;
    }

    @Override
    public void destroy() {
        mMinePresenter = null;
    }

    @Override
    public void loadCollectionListData() {
        HttpInvoker.createBuilder(NetConstant.GET_MINE_COLLECT_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_GET)
                .setClz(MineCollectEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onLoadCollectionListDataSuccess(serverResponse);
            }
        });
    }

    @Override
    public void deleteCollection(MineCollectEntity mineCollectionEntitiy) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", mineCollectionEntitiy.getGoods().getGoods_id() + "");
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_COLLECT_GOOD_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onDeleteCollection(serverResponse);
            }
        });
    }
}
