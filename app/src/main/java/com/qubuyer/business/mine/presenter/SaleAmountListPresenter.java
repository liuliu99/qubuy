package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.model.ISaleAmountModel;
import com.qubuyer.business.mine.model.SaleAmountModel;
import com.qubuyer.business.mine.view.ISaleAmountPageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class SaleAmountListPresenter extends WrapperPresenter<ISaleAmountPageView> implements ISaleAmountListPresenter {
    private ISaleAmountModel mModel;

    public SaleAmountListPresenter() {
        mModel = new SaleAmountModel(this);
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
            SaleAmountEntity entity = (SaleAmountEntity) serverResponse.getResult();
            List<SaleAmountEntity.ListBean> list = entity.getList();
            switch (loadType) {
                case AppConstant.LOAD_TYPE_UP:
                    if (isViewNotNull()) {
                        mView.finishLoadMore(0, true, null == list || list.size() < AppConstant.COUNT_SMAll);
                        mView.onShowLoadMoreListToView(entity);
                    }
                    break;
                default:
                    if (loadType == AppConstant.LOAD_TYPE_DOWN && isViewNotNull()) {
                        mView.finishRefresh(true);
                    }
                    if (isViewNotNull()) {
                        mView.onShowRefreshListToView(entity);
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
