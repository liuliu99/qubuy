package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineFansEntity;
import com.qubuyer.business.mine.model.IMineFansListModel;
import com.qubuyer.business.mine.model.MineFansListModel;
import com.qubuyer.business.mine.view.IMineFansListView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class MineFansListPresenter extends WrapperPresenter<IMineFansListView> implements IMineFansListPresenter {
    private IMineFansListModel model;

    public MineFansListPresenter() {
        model = new MineFansListModel(this);
        attachModel(model);
    }

    @Override
    public void loadFansListData() {
        mView.showLoading();
        model.loadFansListData();
    }

    @Override
    public void onLoadFansListDataSuccess(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowFansListToView((List<MineFansEntity>) serverResponse.getResult());
        } else {
            mView.onShowFansListToView(new ArrayList<>(0));
        }
    }
}
