package com.qubuyer.business.home.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.model.HomeSecondGoodListModel;
import com.qubuyer.business.home.model.IHomeSecondGoodListModel;
import com.qubuyer.business.home.view.IHomeSecondGoodListPageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class HomeSecondGoodListPresenter extends WrapperPresenter<IHomeSecondGoodListPageView> implements IHomeSecondGoodListPresenter {
    private IHomeSecondGoodListModel mModel;

    public HomeSecondGoodListPresenter() {
        mModel = new HomeSecondGoodListModel(this);
        attachModel(mModel);
    }

    @Override
    public void refresh(int type) {
        mModel.getGoodList(AppConstant.LOAD_TYPE_DOWN, type);
    }

    @Override
    public void loadMore(int type) {
        mModel.getGoodList(AppConstant.LOAD_TYPE_UP, type);
    }

    @Override
    public void onGetGoodList(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) serverResponse.getResult();
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
