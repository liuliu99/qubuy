package com.qubuyer.business.shopcart.model;

import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.shopcart.presenter.IShopCartPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class ShopCartModel implements IShopCartModel {
    private IShopCartPresenter mPresenter;
    private int pageNo = 1;

    public ShopCartModel(IShopCartPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
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
    public void getShopCartSpecialGoodList() {
        HttpInvoker.createBuilder(NetConstant.GET_SHOP_CART_SPECIAL_GOOD_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetShopCartSpecialGoodList(serverResponse);
            }
        });
    }

    @Override
    public void deleteGood(String shopCartId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", shopCartId);
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_DELETE_GOOD_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onDeleteGood(serverResponse);
            }
        });
    }

    @Override
    public void collecGood(String goodsId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", goodsId);
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
    public void goodCountChange(String shopCartId, int count) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cart_id", shopCartId);
        map.put("goods_num", count + "");
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_GOOD_COUNT_CHANGE_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGoodCountChange(serverResponse);
            }
        });
    }

    @Override
    public void goodChecked(String shopCartId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", shopCartId);
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_GOOD_CHECKED_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGoodChecked(serverResponse);
            }
        });
    }

    @Override
    public void goodUnCheck(String shopCartId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", shopCartId);
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_GOOD_UNCHECK_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGoodUnCheck(serverResponse);
            }
        });
    }

    @Override
    public void goodAllCheckOrNot(int type) {
        HttpInvoker.createBuilder(type == 1 ? NetConstant.SHOP_CART_GOOD_ALLCHECK_POST : NetConstant.SHOP_CART_GOOD_ALLCHECK_NOT_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGoodAllCheckOrNot(serverResponse);
            }
        });
    }

    @Override
    public void clearLoseEfficacyGood(String ids) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", ids);
        HttpInvoker.createBuilder(NetConstant.SHOP_CART_GOOD_CLEAR_LOSE_EFFICACY_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGoodAllCheckOrNot(serverResponse);
            }
        });
    }

    @Override
    public void calcOrderPrice() {
        HttpInvoker.createBuilder(NetConstant.CALC_ORDER_PRICE_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(CalcOrderPriceResultEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onCalcOrderPrice(serverResponse);
            }
        });
    }
}
