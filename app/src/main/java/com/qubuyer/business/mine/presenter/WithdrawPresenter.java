package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.model.IWalletModel;
import com.qubuyer.business.mine.model.IWithdrawModel;
import com.qubuyer.business.mine.model.WalletModel;
import com.qubuyer.business.mine.model.WithdrawModel;
import com.qubuyer.business.mine.view.IWalletView;
import com.qubuyer.business.mine.view.IWithdrawView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class WithdrawPresenter extends WrapperPresenter<IWithdrawView> implements IWithdrawPresenter {
    private IWithdrawModel mModel;

    public WithdrawPresenter() {
        mModel = new WithdrawModel(this);
        attachModel(mModel);
    }

    @Override
    public void getWalletInfo() {
        mView.showLoading();
        mModel.getWalletInfo();
    }

    @Override
    public void onGetWalletInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShoWalletInfoToView((WalletInfoEntity) serverResponse.getResult());
        } else {
            mView.onShoWalletInfoToView(null);
        }
    }

    @Override
    public void getUserInfo() {
        mView.showLoading();
        mModel.getUserInfo();
    }

    @Override
    public void onGetUserInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowUserInfoToView((UserEntity) serverResponse.getResult());
        } else {
            mView.onShowUserInfoToView(null);
        }
    }

    @Override
    public void getVerificationcode(String phone) {
        mView.showLoading();
        mModel.getVerificationcode(phone);
    }

    @Override
    public void onGetVerificationcode(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowVerificationcodeResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void withdraw(String money, String oauth, String mobile, String code) {
        mView.showLoading();
        mModel.withdraw(money, oauth, mobile, code);
    }

    @Override
    public void onWithdraw(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowWithdrawResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
