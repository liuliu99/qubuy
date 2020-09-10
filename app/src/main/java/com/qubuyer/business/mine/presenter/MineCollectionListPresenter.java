package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineCollectEntity;
import com.qubuyer.business.mine.model.IMineCollectionListModel;
import com.qubuyer.business.mine.model.MineCollectionListModel;
import com.qubuyer.business.mine.view.IMineCollectionListView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class MineCollectionListPresenter extends WrapperPresenter<IMineCollectionListView> implements IMineCollectionListPresenter {
    private IMineCollectionListModel model;

    public MineCollectionListPresenter() {
        model = new MineCollectionListModel(this);
        attachModel(model);
    }

    @Override
    public void loadCollectionListData() {
        mView.showLoading();
        model.loadCollectionListData();
    }

    @Override
    public void onLoadCollectionListDataSuccess(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowCollectListToView((List<MineCollectEntity>) serverResponse.getResult());
        } else {
            mView.onShowCollectListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void deleteCollection(MineCollectEntity mineCollectionEntitiy) {
        model.deleteCollection(mineCollectionEntitiy);
    }

    @Override
    public void onDeleteCollection(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            mView.onDeleteCollectionResult(true);
        } else {
            mView.onDeleteCollectionResult(false);
        }
    }
}
