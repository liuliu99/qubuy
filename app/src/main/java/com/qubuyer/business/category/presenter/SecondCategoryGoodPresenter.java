package com.qubuyer.business.category.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.category.SecondCategoryGoodEntity;
import com.qubuyer.business.category.model.ISecondCategoryGoodModel;
import com.qubuyer.business.category.model.SecondCategoryGoodModel;
import com.qubuyer.business.category.view.ISecondCategoryGoodView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class SecondCategoryGoodPresenter extends WrapperPresenter<ISecondCategoryGoodView> implements ISecondCategoryGoodPresenter {
    private ISecondCategoryGoodModel mModel;

    public SecondCategoryGoodPresenter() {
        mModel = new SecondCategoryGoodModel(this);
        attachModel(mModel);
    }

    @Override
    public void refresh(String firstCategoryId, String secondCategoryId) {
        mView.showLoading();
        mModel.loadData(AppConstant.LOAD_TYPE_DOWN, firstCategoryId, secondCategoryId);
    }

    @Override
    public void loadMore(String firstCategoryId, String secondCategoryId) {
        mModel.loadData(AppConstant.LOAD_TYPE_UP, firstCategoryId, secondCategoryId);
    }

    @Override
    public void onLoadData(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            SecondCategoryGoodEntity entity = (SecondCategoryGoodEntity) serverResponse.getResult();
            switch (loadType) {
                case AppConstant.LOAD_TYPE_UP:
                    if (isViewNotNull()) {
                        mView.finishLoadMore(0, true, null == entity.getList() || null == entity.getList().getData() || entity.getList().getData().size() < AppConstant.COUNT_MAX);
                        mView.onShowLoadMoreGoodDataToView(entity);
                    }
                    break;
                default:
                    if (loadType == AppConstant.LOAD_TYPE_DOWN && isViewNotNull()) {
                        mView.finishRefresh(true);
                    }
                    if (isViewNotNull()) {
                        mView.onShowRefreshDataToView(entity);
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
