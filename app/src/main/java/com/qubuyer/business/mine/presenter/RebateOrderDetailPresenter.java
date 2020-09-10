package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.model.IRebateOrderDetailModel;
import com.qubuyer.business.mine.model.RebateOrderDetailModel;
import com.qubuyer.business.mine.view.IRebateOrderDetailView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;


public class RebateOrderDetailPresenter extends WrapperPresenter<IRebateOrderDetailView> implements IRebateOrderDetailPresenter {
    private IRebateOrderDetailModel mModel;

    public RebateOrderDetailPresenter() {
        mModel = new RebateOrderDetailModel(this);
        attachModel(mModel);
    }

    @Override
    public void getReabteOrderDetail(String id) {
        mView.showLoading();
        mModel.getReabteOrderDetail(id);
    }

    @Override
    public void onGetRebateOrderDetail(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowRebateOrderDetailToView((RebateOrderEntity) serverResponse.getResult());
        } else {
            mView.onShowRebateOrderDetailToView(null);
        }
    }

    @Override
    public void rebateClose(String id) {
        mView.showLoading();
        mModel.rebateClose(id);
    }

    @Override
    public void onReabteClose(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowRebateCloseResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
