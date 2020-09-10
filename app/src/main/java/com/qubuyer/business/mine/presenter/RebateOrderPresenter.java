package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.model.IRebateOrderModel;
import com.qubuyer.business.mine.model.RebateOrderModel;
import com.qubuyer.business.mine.view.IRebateOrderPageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class RebateOrderPresenter extends WrapperPresenter<IRebateOrderPageView> implements IRebateOrderPresenter {
    private IRebateOrderModel mModel;

    public RebateOrderPresenter() {
        mModel = new RebateOrderModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadMore(int orderStatus) {
        mModel.loadAllData(AppConstant.LOAD_TYPE_UP, orderStatus);
    }

    @Override
    public void refresh(int orderStatus) {
        mModel.loadAllData(AppConstant.LOAD_TYPE_DOWN, orderStatus);
    }

    @Override
    public void onLoadDataResult(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<RebateOrderEntity> list = (List<RebateOrderEntity>) serverResponse.getResult();
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
}
