package com.qubuyer.business.good.model;

import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.presenter.IGoodDetailPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class GoodDetailModel implements IGoodDetailModel {
    private IGoodDetailPresenter mPresenter;

    public GoodDetailModel(IGoodDetailPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getGoodDetail(String goodId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", goodId);
        HttpInvoker.createBuilder(NetConstant.GET_GOOD_DETAIL_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(HomeGoodEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetGoodDetail(serverResponse);
            }
        });
    }

    @Override
    public void getShopCartGoodList() {
        HttpInvoker.createBuilder(NetConstant.GET_SHOP_CART_GOOD_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(ShopCartGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetShopCartGoodList(serverResponse);
            }
        });
    }

    @Override
    public void collectGood(String goodId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", goodId);
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_COLLECT_GOOD_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onCollectGood(serverResponse);
            }
        });
    }

    @Override
    public void addGoodToCart(String goodId, int count, String item_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodId);
        map.put("goods_num", count + "");
        map.put("item_id", item_id + "");
        HttpInvoker.createBuilder(NetConstant.ADD_GOOD_TO_CART_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onAddGoodToCart(serverResponse);
            }
        });
    }

    @Override
    public void getGoodCommentList(String goodId, int type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", goodId);
        map.put("type", type + "");
        HttpInvoker.createBuilder(NetConstant.GET_GOOD_COMMMENT_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(GoodCommentEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetGoodCommmentList(serverResponse);
            }
        });
    }

    @Override
    public void getGoodAssess(String goodId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", goodId);
        HttpInvoker.createBuilder(NetConstant.GET_GOOD_ASSESS_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(GoodAssessEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetGoodAssess(serverResponse);
            }
        });
    }
}
