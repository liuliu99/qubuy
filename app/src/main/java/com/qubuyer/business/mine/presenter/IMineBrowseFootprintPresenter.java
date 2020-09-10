package com.qubuyer.business.mine.presenter;


import com.qubuyer.bean.mine.MineBrowseFootprintEntity;
import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineBrowseFootprintPresenter {
    void loadBrowseFootprintListData();
    void onLoadBrowseFootprintListData(ServerResponse serverResponse);

    void deleteBrowseFootprint(MineBrowseFootprintEntity.ValueBean entity);
    void onDeleteBrowseFootprint(ServerResponse serverResponse);

    void clearBrowseFootprint();
    void onClearBrowseFootprint(ServerResponse serverResponse);
}
