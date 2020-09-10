package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.model.IMineModel;
import com.qubuyer.business.mine.model.MineModel;
import com.qubuyer.business.mine.view.IMineView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class MinePresenter extends WrapperPresenter<IMineView> implements IMinePresenter {
    private IMineModel mModel;

    public MinePresenter() {
        mModel = new MineModel(this);
        attachModel(mModel);
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
    public void getUserMessageCount() {
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

    @Override
    public void loginOut() {
        mView.showLoading();
        mModel.loginOut();
    }

    @Override
    public void onLoginOut(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            SessionUtil.getInstance().loginOut();
            mView.onShowLoginOutResultToView(true);
        } else {
            mView.onShowLoginOutResultToView(false);
        }
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
}
