package com.qubuyer.business.good.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ISearchGoodPresenter {
    void loadAllData();
    void loadSearchHistoryInfo();
    void deleteAllSearchHistory();

    void loadSearchResultFirstListByKey(String key);
    void onLoadSearchResultFirstListByKey(ServerResponse serverResponse);
}
