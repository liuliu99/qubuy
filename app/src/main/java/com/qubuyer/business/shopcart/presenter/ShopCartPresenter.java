package com.qubuyer.business.shopcart.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.shopcart.model.IShopCartModel;
import com.qubuyer.business.shopcart.model.ShopCartModel;
import com.qubuyer.business.shopcart.view.IShopCartView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class ShopCartPresenter extends WrapperPresenter<IShopCartView> implements IShopCartPresenter {
    private IShopCartModel mModel;

    public ShopCartPresenter() {
        mModel = new ShopCartModel(this);
        attachModel(mModel);
    }

    @Override
    public void getShopCartGoodList() {
        mView.showLoading();
        mModel.getShopCartGoodList();
    }

    @Override
    public void onGetShopCartGoodList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowShopCartListDataToView((List<ShopCartGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowShopCartListDataToView(new ArrayList<>(0));
        }
    }

    @Override
    public void getShopCartSpecialGoodList() {
//        mView.showLoading();
        mModel.getShopCartSpecialGoodList();
    }

    @Override
    public void onGetShopCartSpecialGoodList(ServerResponse serverResponse) {
//        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowShopCartSpecialListDataToView((List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowShopCartSpecialListDataToView(new ArrayList<>(0));
        }
    }

    @Override
    public void deleteGood(String shopCartId) {
        mView.showLoading();
        mModel.deleteGood(shopCartId);
    }

    @Override
    public void onDeleteGood(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowDeleteGoodResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void collecGood(String goodsId) {
        mView.showLoading();
        mModel.collecGood(goodsId);
    }

    @Override
    public void onCollectGood(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowCollectGoodResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void goodCountChange(String shopCartId, int count) {
        mView.showLoading();
        mModel.goodCountChange(shopCartId, count);
    }

    @Override
    public void onGoodCountChange(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowGoodCountChangeResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void goodChecked(String shopCartId) {
        mView.showLoading();
        mModel.goodChecked(shopCartId);
    }

    @Override
    public void onGoodChecked(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowGoodCheckedResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void goodUnCheck(String shopCartId) {
        mView.showLoading();
        mModel.goodUnCheck(shopCartId);
    }

    @Override
    public void onGoodUnCheck(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowGoodUnCheckResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void goodAllCheckOrNot(int type) {
        mView.showLoading();
        mModel.goodAllCheckOrNot(type);
    }

    @Override
    public void onGoodAllCheckOrNot(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowGoodAllCheckOrNotResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void clearLoseEfficacyGood(List<ShopCartGoodEntity> list) {
        mView.showLoading();
        StringBuffer stringBuffer = new StringBuffer();
        for (ShopCartGoodEntity entity : list) {
            stringBuffer.append(entity.getId() + ",");
        }
        mModel.clearLoseEfficacyGood(stringBuffer.toString());
    }

    @Override
    public void onClearLoseEfficacyGood(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowClearLoseEfficacyResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    public void calcOrderPrice(){
        mView.showLoading();
        mModel.calcOrderPrice();
    }

    public void onCalcOrderPrice(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowCalcOrderPriceResultToView((CalcOrderPriceResultEntity) serverResponse.getResult());
        } else {
            mView.onShowCalcOrderPriceResultToView(null);
        }
    }
}
