package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.order.model.IOrderRefundListModel;
import com.qubuyer.business.order.model.OrderRefundListModel;
import com.qubuyer.business.order.view.IOrderRefundListView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class OrderRefundListPresenter extends WrapperPresenter<IOrderRefundListView> implements IOrderRefundListPresenter{
    private IOrderRefundListModel mModel;

    public OrderRefundListPresenter() {
        mModel = new OrderRefundListModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadMore() {
        mModel.getRefundList(AppConstant.LOAD_TYPE_UP);
    }

    @Override
    public void refresh() {
        mModel.getRefundList(AppConstant.LOAD_TYPE_DOWN);
    }

    @Override
    public void onLoadListResult(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<OrderRefundEntity> list = (List<OrderRefundEntity>) serverResponse.getResult();
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
    public void getRefundDetail(String id) {
        mView.showLoading();
        mModel.getRefundDetail(id);
    }

    @Override
    public void onGetRefundDetail(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowRefundDetailToView((OrderRefundEntity) serverResponse.getResult());
        } else {
            mView.onShowRefundDetailToView(null);
        }
    }

    @Override
    public void cancelRefund(String id) {
        mView.showLoading();
        mModel.cancelRefund(id);
    }

    @Override
    public void onCancelRefund(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowCancelRefundResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
