package com.qubuyer.business.category.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface ICategoryPresenter {
    void loadFirstCategory();
    void onLoadFirstCategory(ServerResponse serverResponse);
}
