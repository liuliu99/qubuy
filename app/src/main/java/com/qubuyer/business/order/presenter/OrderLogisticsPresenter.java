package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderLogisticsEntity;
import com.qubuyer.business.order.model.IOrderLogisticsModel;
import com.qubuyer.business.order.model.OrderLogisticsModel;
import com.qubuyer.business.order.view.IOrderLogisticsView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;


public class OrderLogisticsPresenter extends WrapperPresenter<IOrderLogisticsView> implements IOrderLogisticsPresenter{
    private IOrderLogisticsModel mModel;

    public OrderLogisticsPresenter() {
        mModel = new OrderLogisticsModel(this);
        attachModel(mModel);
    }

    @Override
    public void getLogisticsList(String id) {
        mView.showLoading();
        mModel.getLogisticsList(id);
    }

    @Override
    public void onGetLogisticsList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowLogisticsListToView((OrderLogisticsEntity) serverResponse.getResult());
        } else {
            mView.onShowLogisticsListToView(null);
        }
    }
}
