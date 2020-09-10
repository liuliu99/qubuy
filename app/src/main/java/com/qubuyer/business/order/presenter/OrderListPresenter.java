package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.model.IOrderListModel;
import com.qubuyer.business.order.model.OrderListModel;
import com.qubuyer.business.order.view.IOrderListPageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;


public class OrderListPresenter extends WrapperPresenter<IOrderListPageView> implements IOrderListPresenter {
    private IOrderListModel mModel;

    public OrderListPresenter() {
        mModel = new OrderListModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadMore(String orderStatus) {
//        mModel.loadTicketListData(AppConstant.LOAD_TYPE_UP, orderStatus);
//        mModel.loadFoodListData(AppConstant.LOAD_TYPE_UP, orderStatus);
        //  mModel.loadHotelListData(AppConstant.LOAD_TYPE_UP, orderStatus);
//        mModel.loadFruitsListData(AppConstant.LOAD_TYPE_UP, orderStatus);
        mModel.loadAllData(AppConstant.LOAD_TYPE_UP, orderStatus);
    }

    @Override
    public void refresh(String orderStatus) {
//        mModel.loadTicketListData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
//        mModel.loadFoodListData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
        //        mModel.loadHotelListData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
//        mModel.loadFruitsListData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
        mModel.loadAllData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
    }

    @Override
    public void onLoadOrderListSuccess(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<OrderEntity> list = (List<OrderEntity>) serverResponse.getResult();
            switch (loadType) {
                case AppConstant.LOAD_TYPE_UP:
                    if (isViewNotNull()) {
                        mView.finishLoadMore(0, true, null == list || list.size() < AppConstant.COUNT_SMAll);
                        mView.onShowLoadMoreListToView(list);
                    }
                    break;
                default:
                    if (loadType == AppConstant.LOAD_TYPE_DOWN && isViewNotNull()) {
                        mView.finishRefresh(true);
                    }
                    if (isViewNotNull()) {
                        mView.onShowRefreshListToView(list);
                    }
                    break;
            }
        } else {
            if (isViewNotNull()) {
                switch (loadType) {
                    case AppConstant.LOAD_TYPE_UP:
                        mView.finishLoadMore(0, false, true);
                        break;
                    case AppConstant.LOAD_TYPE_DOWN:
                        mView.finishRefresh(false);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void cancelOrder(String orderId, String reasonId) {
        mView.showLoading();
        mModel.cancelOrder(orderId, reasonId);
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
