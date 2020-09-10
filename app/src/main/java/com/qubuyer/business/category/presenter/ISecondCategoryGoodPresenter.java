package com.qubuyer.business.category.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ISecondCategoryGoodPresenter {
    void refresh(String firstCategoryId,String secondCategoryId);
    void loadMore(String firstCategoryId,String secondCategoryId);
    void onLoadData(int loadType, ServerResponse serverResponse);
}
