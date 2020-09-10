package com.qubuyer.business.home.presenter;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.business.home.model.HomeModel;
import com.qubuyer.business.home.model.IHomeModel;
import com.qubuyer.business.home.view.IHomeView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePresenter extends WrapperPresenter<IHomeView> implements IHomePresenter {
    private IHomeModel mModel;

    public HomePresenter() {
        mModel = new HomeModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadBanner() {
        if (NetworkUtils.isConnected()) {
            mModel.loadBanner();
        } else {
            List<HomeBannerEntity> homeBannerEntityList = (List<HomeBannerEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_BANNER_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return homeBannerEntityList;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadBannerSuccess(serverResponse);
        }
    }

    @Override
    public void onLoadBannerSuccess(ServerResponse serverResponse) {
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        List<HomeBannerEntity> list;
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            list = (List<HomeBannerEntity>) serverResponse.getResult();
        } else {
            list = new ArrayList<>();
        }
        if (isViewNotNull()) {
            mView.onShowBannerToView(list);
        }
        CacheDoubleUtils.getInstance().put(AppConstant.KEY_HOME_BANNER_CACHE, (Serializable) list);
    }

    @Override
    public void loadCategory() {
        if (NetworkUtils.isConnected()) {
            mModel.loadCategory();
        } else {
            List<HomeCategoryEntity> homeCategoryEntityList = (List<HomeCategoryEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_CATEGORY_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return homeCategoryEntityList;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadCategorySuccess(serverResponse);
        }
    }

    @Override
    public void onLoadCategorySuccess(ServerResponse serverResponse) {
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        List<HomeCategoryEntity> list;
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            list = (List<HomeCategoryEntity>) serverResponse.getResult();
        } else {
            list = new ArrayList<>(0);
        }
        mView.onShowCategoryToView(list);
        CacheDoubleUtils.getInstance().put(AppConstant.KEY_HOME_CATEGORY_CACHE, (Serializable) list);
    }

    @Override
    public void loadSaleTop() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSaleTop();
        } else {
            List<HomeSaleTopEntity> list = (List<HomeSaleTopEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_SALE_TOP_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSaleTop(serverResponse);
        }
    }

    @Override
    public void onLoadSaleTop(ServerResponse serverResponse) {
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        HomeSaleTopEntity entity = null;
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            entity = (HomeSaleTopEntity) serverResponse.getResult();
        }
        if (isViewNotNull()) {
            mView.onShowSaleTopToView(entity);
        }
        CacheDoubleUtils.getInstance().put(AppConstant.KEY_HOME_SALE_TOP_CACHE, entity);
    }

    @Override
    public void loadUserInfo() {
        if (SessionUtil.getInstance().isLogined()) {
            if (NetworkUtils.isConnected()) {
                mModel.loadUserInfo();
            } else {
                UserEntity entity = (UserEntity) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_SALE_TOP_USER_MONEY_CACHE);
                ServerResponse serverResponse = new ServerResponse() {
                    @Override
                    public Object getResult() {
                        return entity;
                    }

                    @Override
                    public int getCode() {
                        return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                    }
                };
                onLoadUserInfo(serverResponse);
            }
        }
    }

    @Override
    public void onLoadUserInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowUserInfoToView((UserEntity) serverResponse.getResult());
        } else {
            mView.onShowUserInfoToView(null);
        }
    }

    @Override
    public void loadSpecialChoicenessGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSpecialInfo("fine");
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_CHOICENESS_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSpecialChoicenessGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadSpecialChoicenessGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowChoicenessGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowChoicenessGoodListToView(new ArrayList<>(0));
        }
        loadSpecialLimitGoodInfo();
    }

    @Override
    public void loadSpecialLimitGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSpecialInfo(null);
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_LIMIT_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSpecialLimitGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadSpecialLimitGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowLimitGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowLimitGoodListToView(new ArrayList<>(0));
        }
        loadSpecialSpecialGoodInfo();
    }

    @Override
    public void loadSpecialSpecialGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSpecialInfo("hot");
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_SPECIAL_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSpecialSpecialGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadSpecialSpecialGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowSpecialGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowSpecialGoodListToView(new ArrayList<>(0));
        }
        loadSpecialFirstpublishGoodInfo();
    }

    @Override
    public void loadSpecialFirstpublishGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSpecialInfo("new");
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_FIRSTPUBLISH_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSpecialFirstpublishGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadSpecialFirstpublishGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowFirstpublishGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowFirstpublishGoodListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void loadSuperReturnGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadSuperReturnInfo();
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_SUPER_RETURN_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadSuperReturnGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadSuperReturnGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowSuperReturnGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowSuperReturnGoodListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void loadDailyDiscountGoodInfo() {
        if (NetworkUtils.isConnected()) {
            mModel.loadDailyDiscountInfo();
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_DAILYDISCOUNT_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }

                @Override
                public int getCode() {
                    return HttpConstant.Status.SERVER_STATUS_SUCCESSFUL;
                }
            };
            onLoadDailyDiscountGoodInfo(serverResponse);
        }
    }

    @Override
    public void onLoadDailyDiscountGoodInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowDailyDiscountGoodListToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowDailyDiscountGoodListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void refresh(int goodType) {
        loadBanner();
        loadCategory();
        loadSaleTop();
        loadUserInfo();
        loadSpecialChoicenessGoodInfo();
        loadSuperReturnGoodInfo();
        loadDailyDiscountGoodInfo();

        if (NetworkUtils.isConnected()) {
            mModel.loadGoodList(false, goodType, AppConstant.LOAD_TYPE_DOWN);
        } else {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) CacheDoubleUtils.getInstance().getSerializable(AppConstant.KEY_HOME_GOODS_LIST_CACHE);
            ServerResponse serverResponse = new ServerResponse() {
                @Override
                public Object getResult() {
                    return list;
                }
            };
            onLoadGoodsSuccess(null, serverResponse, AppConstant.LOAD_TYPE_DOWN);
        }
    }

    @Override
    public void loadGoodList(int goodType) {
        mView.showLoading();
        mModel.loadGoodList(true, goodType, AppConstant.LOAD_TYPE_UP);
    }

//    @Override
//    public void loadMore(int goodType) {
//        if (NetworkUtils.isConnected()) {
//            mModel.loadGoodList(AppConstant.LOAD_TYPE_UP);
//        } else {
//            ServerResponse serverResponse = new ServerResponse() {
//                @Override
//                public Object getResult() {
//                    return null;
//                }
//            };
//            onLoadGoodsSuccess(AppConstant.LOAD_TYPE_UP, serverResponse);
//        }
//    }

    @Override
    public void onLoadGoodsSuccess(Map<String, String> requestParams, ServerResponse serverResponse, int loadType) {
        mView.hideLoading();
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            ArrayList<HomeGoodEntity> list = (ArrayList<HomeGoodEntity>) serverResponse.getResult();
            switch (loadType) {
                case AppConstant.LOAD_TYPE_UP:
                    if (isViewNotNull()) {
                        mView.finishLoadMore(0, true, null == list || list.size() < AppConstant.COUNT_SMAll);
                        mView.onShowLoadMoreGoodDataToView(list);
                    }
                    break;
                default:
                    if (loadType == AppConstant.LOAD_TYPE_DOWN && isViewNotNull()) {
                        mView.finishRefresh(true);
                    }
                    if (isViewNotNull()) {
                        if (null == requestParams || null == requestParams.get("isclick") || !requestParams.get("isclick").equals("true")) {
                            if (NetworkUtils.isConnected()) {
                                CacheDoubleUtils.getInstance().put(AppConstant.KEY_HOME_GOODS_LIST_CACHE, list);
                            }
                            mView.onShowRefreshDataToView(list);
                        } else {
                            mView.onShowClickDataToView(list);
                        }
                    }
                    break;
            }
        } else {
//            if (isViewNotNull()) {
//                if (null == requestParams || null == requestParams.get("isclick") || !requestParams.get("isclick").equals("true")) {
//                    mView.finishRefresh(false);
//                    mView.onShowRefreshDataToView(new ArrayList<>(0));
//                } else {
//                    mView.onShowClickDataToView(new ArrayList<>(0));
//                }
//            }
            if (isViewNotNull()) {
                switch (loadType) {
                    case AppConstant.LOAD_TYPE_UP:
                        mView.finishLoadMore(0, false, true);
                        break;
                    case AppConstant.LOAD_TYPE_DOWN:
                        mView.finishRefresh(false);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void getUserMessageCount() {
        if (!SessionUtil.getInstance().isLogined()) return;
        mView.showLoading();
        mModel.getUserMessageCount();
    }

    @Override
    public void onGetUserMessageCount(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowUserMessageCountDataToView((UserMessageCountEntity) serverResponse.getResult());
        }
    }
}
