package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.model.IMineSettingModel;
import com.qubuyer.business.mine.model.MineSettingModel;
import com.qubuyer.business.mine.view.IMineSettingView;
import com.qubuyer.business.mine.view.IMineView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.io.File;

public class MineSettingPresenter extends WrapperPresenter<IMineSettingView> implements IMineSettingPresenter {
    private IMineSettingModel mModel;

    public MineSettingPresenter() {
        mModel = new MineSettingModel(this);
        attachModel(mModel);
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
    public void updateHeadImg(File file) {
        mView.showLoading();
        mModel.updateHeadImg(file);
    }

    @Override
    public void onUpdateHeadImg(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowUpdateUserHeadImgResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
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
