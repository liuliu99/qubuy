package com.qubuyer.business.mine.presenter;


import com.qubuyer.bean.mine.MineCollectEntity;
import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineCollectionListPresenter {
    void loadCollectionListData();
    void onLoadCollectionListDataSuccess(ServerResponse serverResponse);

    void deleteCollection(MineCollectEntity entity);
    void onDeleteCollection(ServerResponse serverResponse);
}
