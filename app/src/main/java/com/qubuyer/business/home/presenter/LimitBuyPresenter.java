package com.qubuyer.business.home.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.model.ILimitBuyModel;
import com.qubuyer.business.home.model.LimitBuyModel;
import com.qubuyer.business.home.view.ILimitBuyView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class LimitBuyPresenter extends WrapperPresenter<ILimitBuyView> implements ILimitBuyPresenter {
    private ILimitBuyModel mModel;

    public LimitBuyPresenter() {
        mModel = new LimitBuyModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadLimitBuyData() {
        mView.showLoading();
        mModel.loadLimitBuyData();
    }

    @Override
    public void onLoadLimitBuyData(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowLimitBuyDataToView(serverResponse.getTime(), (List<HomeGoodEntity>) serverResponse.getResult());
        } else {
            mView.onShowLimitBuyDataToView(serverResponse.getTime(), new ArrayList<>(0));
        }
    }
}
