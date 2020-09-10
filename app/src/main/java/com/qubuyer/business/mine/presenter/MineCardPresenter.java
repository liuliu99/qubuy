package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.MinePosterEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.model.IMineCardModel;
import com.qubuyer.business.mine.model.MineCardModel;
import com.qubuyer.business.mine.view.IMineCardView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class MineCardPresenter extends WrapperPresenter<IMineCardView> implements IMineCardPresenter {
    private IMineCardModel mModel;

    public MineCardPresenter() {
        mModel = new MineCardModel(this);
        attachModel(mModel);
    }

    @Override
    public void getQrCode() {
        mView.showLoading();
        mModel.getQrCode();
    }

    @Override
    public void onGetQrCode(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowQrCodeInfoToView((MineCardEntity) serverResponse.getResult());
        } else {
            mView.onShowQrCodeInfoToView(null);
        }
    }

    @Override
    public void getPoster() {
        mView.showLoading();
        mModel.getPoster();
    }

    @Override
    public void onGetPoster(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowPosterInfoToView((MinePosterEntity) serverResponse.getResult());
        } else {
            mView.onShowPosterInfoToView(null);
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
}
