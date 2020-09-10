package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.model.IOrderDetailModel;
import com.qubuyer.business.order.model.OrderDetailModel;
import com.qubuyer.business.order.view.IOrderDetailView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailPresenter extends WrapperPresenter<IOrderDetailView> implements IOrderDetailPresenter{
    private IOrderDetailModel mModel;

    public OrderDetailPresenter() {
        mModel = new OrderDetailModel(this);
        attachModel(mModel);
    }

    @Override
    public void getOrderDetail(String orderId, String sid) {
        mView.showLoading();
        mModel.getOrderDetail(orderId, sid);
    }

    @Override
    public void onGetOrderDetail(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowOrderDetailToView((OrderEntity) serverResponse.getResult());
        } else {
            mView.onShowOrderDetailToView(null);
        }
    }

    @Override
    public void addGoodToCart(String goodId, int count, String item_id) {
        mView.showLoading();
        mModel.addGoodToCart(goodId, count, item_id);
    }

    @Override
    public void onAddGoodToCart(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowAddGoodToCartResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void cancelOrder(String orderId, String cancelId) {
        mView.showLoading();
        mModel.cancelOrder(orderId, cancelId);
    }

    @Override
    public void onCancelOrder(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowCancelOrderResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void delOrder(String orderId) {
        mView.showLoading();
        mModel.delOrder(orderId);
    }

    @Override
    public void onDelOrder(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowDelOrderResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void confirmGood(String orderId) {
        mView.showLoading();
        mModel.confirmGood(orderId);
    }

    @Override
    public void onConfirmGood(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowConfirmGoodResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void getCancelReasonList() {
        mView.showLoading();
        mModel.getCancelReasonList();
    }

    @Override
    public void onGetCancelReasonList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowCancelReasonListToView((List<OrderRefundReasonEntity>) serverResponse.getResult());
        } else {
            mView.onShowCancelReasonListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void extendReceiving(String orderId) {
        mView.showLoading();
        mModel.extendReceiving(orderId);
    }

    @Override
    public void onExtendReceiving(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowExtendReceivingResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void applyInvoice(String id, String sid, String invoice_taxpayer, String invoice_mobile, String invoice_email, String invoice_title, String invoice_name, String invoice_desc, String invoice_type) {
        mView.showLoading();
        mModel.applyInvoice(id, sid, invoice_taxpayer, invoice_mobile, invoice_email, invoice_title, invoice_name, invoice_desc, invoice_type);
    }

    @Override
    public void onApplyInvoice(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowApplyInvoiceResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
