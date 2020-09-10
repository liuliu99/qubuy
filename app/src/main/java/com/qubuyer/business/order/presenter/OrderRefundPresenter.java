package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.model.IOrderRefundModel;
import com.qubuyer.business.order.model.OrderRefundModel;
import com.qubuyer.business.order.view.IOrderRefundView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;


public class OrderRefundPresenter extends WrapperPresenter<IOrderRefundView> implements IOrderRefundPresenter{
    private IOrderRefundModel mModel;

    public OrderRefundPresenter() {
        mModel = new OrderRefundModel(this);
        attachModel(mModel);
    }

    @Override
    public void getRefundReasonList() {
        mView.showLoading();
        mModel.getRefundReasonList();
    }

    @Override
    public void onGetRefundReasonList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowRefundReasonListToView((List<OrderRefundReasonEntity>) serverResponse.getResult());
        } else {
            mView.onShowRefundReasonListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void submitRefund(String rec_id, String ids, String money, String note, List<String> imgs) {
        mView.showLoading();
        mModel.submitRefund(rec_id, ids, money, note, imgs);
    }

    @Override
    public void onSubmitRefund(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowSubmitRefundResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
