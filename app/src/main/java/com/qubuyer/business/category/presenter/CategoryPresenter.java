package com.qubuyer.business.category.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.business.category.model.CategoryModel;
import com.qubuyer.business.category.model.ICategoryModel;
import com.qubuyer.business.category.view.ICategoryView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class CategoryPresenter extends WrapperPresenter<ICategoryView> implements ICategoryPresenter {
    private ICategoryModel mModel;

    public CategoryPresenter() {
        mModel = new CategoryModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadFirstCategory() {
        mView.showLoading();
        mModel.loadFirstCategory();
    }

    @Override
    public void onLoadFirstCategory(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowFirstCategoryToView((List<CategoryFirstEntity>) serverResponse.getResult());
        } else {
            mView.onShowFirstCategoryToView(new ArrayList<>(0));
        }
    }
}
