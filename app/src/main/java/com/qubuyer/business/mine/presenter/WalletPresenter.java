package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.model.IWalletModel;
import com.qubuyer.business.mine.model.WalletModel;
import com.qubuyer.business.mine.view.IWalletView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class WalletPresenter extends WrapperPresenter<IWalletView> implements IWalletPresenter {
    private IWalletModel mModel;

    public WalletPresenter() {
        mModel = new WalletModel(this);
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
}
