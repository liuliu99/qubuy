package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.IncomeEntity;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.model.IIncomeModel;
import com.qubuyer.business.mine.model.IncomeModel;
import com.qubuyer.business.mine.view.IIncomePageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class IncomeListPresenter extends WrapperPresenter<IIncomePageView> implements IIncomeListPresenter {
    private IIncomeModel mModel;

    public IncomeListPresenter() {
        mModel = new IncomeModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadMore(String type) {
        mModel.loadAllData(AppConstant.LOAD_TYPE_UP, type);
    }

    @Override
    public void refresh(String type) {
        mModel.loadAllData(AppConstant.LOAD_TYPE_DOWN, type);
    }

    @Override
    public void onLoadDataResult(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<IncomeEntity> list = (List<IncomeEntity>) serverResponse.getResult();
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
