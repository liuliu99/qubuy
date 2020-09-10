package com.qubuyer.business.mine.presenter;

import com.blankj.utilcode.util.ObjectUtils;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineBrowseFootprintEntity;
import com.qubuyer.business.mine.model.IMineBrowseFootprintListModel;
import com.qubuyer.business.mine.model.MineBrowseFootprintListModel;
import com.qubuyer.business.mine.view.IMineBrowseFootprintListView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class MineBrowseFootprintListPresenter extends WrapperPresenter<IMineBrowseFootprintListView> implements IMineBrowseFootprintPresenter {
    private IMineBrowseFootprintListModel model;

    public MineBrowseFootprintListPresenter() {
        model = new MineBrowseFootprintListModel(this);
        attachModel(model);
    }

    @Override
    public void loadBrowseFootprintListData() {
        mView.showLoading();
        model.loadBrowseFootprintListData();
    }

    @Override
    public void onLoadBrowseFootprintListData(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<MineBrowseFootprintEntity> mineBrowseFootprintEntityList = (List<MineBrowseFootprintEntity>) serverResponse.getResult();
            List<MineBrowseFootprintEntity.ValueBean> valueBeanList = new ArrayList<>();
            for (MineBrowseFootprintEntity mineBrowseFootprintEntity : mineBrowseFootprintEntityList) {
                String time = mineBrowseFootprintEntity.getTime();
                for (MineBrowseFootprintEntity.ValueBean valueBean : mineBrowseFootprintEntity.getValue()) {
                    valueBean.setTime(time);
                }
                valueBeanList.addAll(mineBrowseFootprintEntity.getValue());
            }
            mView.onShowBrowseFootprintListToView(valueBeanList);
        } else {
            mView.onShowBrowseFootprintListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void deleteBrowseFootprint(MineBrowseFootprintEntity.ValueBean entity) {
        if (ObjectUtils.isEmpty(entity)) return;
        model.deleteBrowseFootprint(String.valueOf(entity.getId()));
    }

    @Override
    public void onDeleteBrowseFootprint(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            mView.onDeleteBrowseFootprintResult(true);
        } else {
            mView.onDeleteBrowseFootprintResult(false);
        }
    }

    @Override
    public void clearBrowseFootprint() {
        model.clearBrowseFootprint();
    }

    @Override
    public void onClearBrowseFootprint(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            mView.onClearBrowseFootprintResult(true);
        } else {
            mView.onClearBrowseFootprintResult(false);
        }
    }
}
