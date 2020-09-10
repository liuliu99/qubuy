package com.qubuyer.business.good.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.model.GoodDetailModel;
import com.qubuyer.business.good.model.IGoodDetailModel;
import com.qubuyer.business.good.view.IGoodDetailView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class GoodDetailPresenter extends WrapperPresenter<IGoodDetailView> implements IGoodDetailPresenter {
    private IGoodDetailModel mModel;

    public GoodDetailPresenter() {
        mModel = new GoodDetailModel(this);
        attachModel(mModel);
    }

    @Override
    public void getGoodDetail(String goodId) {
        mView.showLoading();
        mModel.getGoodDetail(goodId);
    }

    @Override
    public void onGetGoodDetail(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowGoodDetailToView((HomeGoodEntity) serverResponse.getResult());
        } else {
            mView.onShowGoodDetailToView(null);
        }
    }

    @Override
    public void getShopCartGoodList() {
        if (SessionUtil.getInstance().isLogined()) {
//            mView.showLoading();
            mModel.getShopCartGoodList();
        }
    }

    @Override
    public void onGetShopCartGoodList(ServerResponse serverResponse) {
//        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowShopCartListDataToView((List<ShopCartGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowShopCartListDataToView(new ArrayList<>(0));
        }
    }

    @Override
    public void collectGood(String goodId) {
        mView.showLoading();
        mModel.collectGood(goodId);
    }

    @Override
    public void onCollectGood(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowCollectGoodResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void addGoodToCart(String goodId, int count, String item_id) {
        mView.showLoading();
        mModel.addGoodToCart(goodId, count, item_id);
    }

    @Override
    public void onAddGoodToCart(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowAddGoodToCartResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void getGoodCommentList(String goodId, int type) {
        mView.showLoading();
        mModel.getGoodCommentList(goodId, type);
    }

    @Override
    public void onGetGoodCommmentList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowGoodCommentListToView((List<GoodCommentEntity>) serverResponse.getResult());
        } else {
            mView.onShowGoodCommentListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void getGoodAssess(String goodId) {
//        mView.showLoading();
        mModel.getGoodAssess(goodId);
    }

    @Override
    public void onGetGoodAssess(ServerResponse serverResponse) {
//        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowGoodAssessToView((GoodAssessEntity) serverResponse.getResult());
        } else {
            mView.onShowGoodAssessToView(null);
        }
    }
}
