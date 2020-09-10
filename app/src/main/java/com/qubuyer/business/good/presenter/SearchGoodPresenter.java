package com.qubuyer.business.good.presenter;

import android.text.TextUtils;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.good.model.ISearchGoodModel;
import com.qubuyer.business.good.model.SearchGoodModel;
import com.qubuyer.business.good.operation.SearchGoodHistoryManager;
import com.qubuyer.business.good.view.ISearchGoodView;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.List;

public class SearchGoodPresenter extends WrapperPresenter<ISearchGoodView> implements ISearchGoodPresenter {
    private ISearchGoodModel mModel;

    public SearchGoodPresenter() {
        mModel = new SearchGoodModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadAllData() {
        loadSearchHistoryInfo();
    }

    @Override
    public void loadSearchHistoryInfo() {
        List<HomeGoodEntity> list = SearchGoodHistoryManager.getInstance().getAll();
        mView.onShowSearchHistoryToView(list);
    }

    @Override
    public void deleteAllSearchHistory() {
        SearchGoodHistoryManager.getInstance().popAll();
        mView.onShowSearchHistoryToView(null);
    }

    @Override
    public void loadSearchResultFirstListByKey(String key) {
        mView.showLoading();
        SearchGoodHistoryManager.getInstance().push(new HomeGoodEntity(!TextUtils.isEmpty(key) ? key : ""));
        mModel.loadSearchResultFirstList(key);
    }

    @Override
    public void onLoadSearchResultFirstListByKey(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (null != serverResponse.getResult()) {
            List<HomeGoodEntity> list = (List<HomeGoodEntity>) serverResponse.getResult();
            mView.onShowSearchResultFirstListToView(list);
        }
    }
}
