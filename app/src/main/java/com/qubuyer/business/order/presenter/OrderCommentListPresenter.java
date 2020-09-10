package com.qubuyer.business.order.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.order.OrderCommentSubmitParamEntity;
import com.qubuyer.business.order.model.IOrderCommentListModel;
import com.qubuyer.business.order.model.OrderCommentListModel;
import com.qubuyer.business.order.view.IOrderCommentListView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class OrderCommentListPresenter extends WrapperPresenter<IOrderCommentListView> implements IOrderCommentListPresenter{
    private IOrderCommentListModel mModel;

    public OrderCommentListPresenter() {
        mModel = new OrderCommentListModel(this);
        attachModel(mModel);
    }

    @Override
    public void submitComment(String orderId, List<OrderCommentSubmitParamEntity> goodList) {
        mView.showLoading();
        mModel.submitComment(orderId, goodList);
    }

    @Override
    public void onSubmitComment(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowSumitCommentResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
