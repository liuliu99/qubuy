package com.qubuyer.business.home.model;

import android.text.TextUtils;

import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.business.home.presenter.IHomePresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class HomeModel implements IHomeModel {
    private IHomePresenter mPresenter;
    private int pageNo = 1;

    public HomeModel(IHomePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void loadBanner() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_BANNER_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeBannerEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadBannerSuccess(serverResponse);
            }
        });
    }

    @Override
    public void loadCategory() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_CATEGORY_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeCategoryEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadCategorySuccess(serverResponse);
            }
        });
    }

    @Override
    public void loadSaleTop() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_SALE_TOP_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeSaleTopEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadSaleTop(serverResponse);
            }
        });
    }

    @Override
    public void loadUserInfo() {
        HttpInvoker.createBuilder(NetConstant.GET_USER_INFO_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(UserEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadUserInfo(serverResponse);
            }
        });
    }

    @Override
    public void loadSpecialInfo(String type) {
        Map<String, String> map = null;
        if (!TextUtils.isEmpty(type)) {
            map = new HashMap<>();
            map.put("type", type);
        } else {
            map = new HashMap<>();
            map.put("limit", "2");
        }
        HttpInvoker.createBuilder(!TextUtils.isEmpty(type) ? NetConstant.GET_HOME_SPECIAL_POST : NetConstant.GET_HOME_LIMITED_BUY_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                if (!TextUtils.isEmpty(type)) {
                    switch (type) {
                        case "hot": //热卖
                            mPresenter.onLoadSpecialSpecialGoodInfo(serverResponse);
                            break;
                        case "new": //新品
                            mPresenter.onLoadSpecialFirstpublishGoodInfo(serverResponse);
                            break;
                        case "fine": //精选
                            mPresenter.onLoadSpecialChoicenessGoodInfo(serverResponse);
                            break;
                    }
                } else {
                    mPresenter.onLoadSpecialLimitGoodInfo(serverResponse);
                }
            }
        });
    }

    @Override
    public void loadSuperReturnInfo() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_SUPER_RETURN_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadSuperReturnGoodInfo(serverResponse);
            }
        });
    }

    @Override
    public void loadDailyDiscountInfo() {
        HttpInvoker.createBuilder(NetConstant.GET_HOME_DAILYDISCOUNT_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadDailyDiscountGoodInfo(serverResponse);
            }
        });
    }

    @Override
    public void loadGoodList(boolean isClick, int goodType, int loadType) {
        Map<String, String> map = new HashMap<>();
        map.put("isclick", isClick + "");
        String url = null;
        switch (goodType) {
            case 1:
                url = NetConstant.GET_HOME_SUPER_RETURN_POST;
                break;
            case 2:
                url = NetConstant.GET_HOME_LIMITED_BUY_POST;
                break;
            case 3:
                url = NetConstant.GET_HOME_HOT_RECOMMEND_POST;
                map.put("limit", "10");
                if (loadType == AppConstant.LOAD_TYPE_UP) {
                    pageNo++;
                } else {
                    pageNo = 1;
                }
                map.put("page", pageNo + "");
                break;
        }
        HttpInvoker.createBuilder(url)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(HomeGoodEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadGoodsSuccess(requestParams, serverResponse, loadType);
            }
        });
    }

    @Override
    public void getUserMessageCount() {
        HttpInvoker.createBuilder(NetConstant.GET_USER_MESSAGE_COUNT_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(UserMessageCountEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetUserMessageCount(serverResponse);
            }
        });
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }
}
