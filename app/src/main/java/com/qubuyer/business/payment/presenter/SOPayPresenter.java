package com.qubuyer.business.payment.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.UserWalletEntity;
import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.bean.payment.PayResultEntity;
import com.qubuyer.bean.payment.WechatPayParamEntity;
import com.qubuyer.business.payment.model.ISOPayModel;
import com.qubuyer.business.payment.model.SOPayModel;
import com.qubuyer.business.payment.view.ISOPayView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;

public class SOPayPresenter extends WrapperPresenter<ISOPayView> implements ISOPayPresenter {
    private ISOPayModel model;

    public SOPayPresenter() {
        model = new SOPayModel(this);
        attachModel(model);
    }

    @Override
    public void loadPayListData() {
        mView.showLoading();
        model.loadPayListData();
    }

    @Override
    public void onLoadPayListData(List<PayListEntity> list) {
        mView.hideLoading();
        mView.onShowPayListDataToView(list);
    }

    @Override
    public void getWechatPayParam(String orderNo) {
        mView.showLoading();
        model.getWechatPayParam(orderNo);
    }

    @Override
    public void onGetWechatPayParam(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowWechatParamToView((WechatPayParamEntity) serverResponse.getResult());
        } else {
            mView.onShowWechatParamToView(null);
        }
    }

    @Override
    public void getAlipayParam(String orderNo) {
        mView.showLoading();
        model.getAlipayParam(orderNo);
    }

    @Override
    public void onGetAlipayParam(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowAlipayParamToView((String) serverResponse.getResult());
        } else {
            mView.onShowAlipayParamToView(null);
        }
    }

    @Override
    public void getOrderPayResult(String orderNo) {
        mView.showLoading();
        model.getOrderPayResult(orderNo);
    }

    @Override
    public void onGetOrderPayResult(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowPayResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL, serverResponse.getMessage(), null != serverResponse.getResult() ? (PayResultEntity) serverResponse.getResult() : null);
    }

    @Override
    public void getUserWallet() {
//        UserInfoEntitiy userInfoEntitiy = DataManager.getInstance().getUserInfo();
//        if (null != userInfoEntitiy && userInfoEntitiy.getLevel() > 1) {
//            mView.showLoading();
//            model.getUserWallet();
//        }
    }

    @Override
    public void onGetUserWallet(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowUserWalletToView((UserWalletEntity) serverResponse.getResult());
        } else {
            mView.onShowUserWalletToView(null);
        }
    }
}
