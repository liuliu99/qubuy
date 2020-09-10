package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineLotteryRecordEntity;
import com.qubuyer.business.mine.model.IMineLotteryRecordModel;
import com.qubuyer.business.mine.model.MineLotteryRecordModel;
import com.qubuyer.business.mine.view.IMineLotteryRecordView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class MineLotteryRecordPresenter extends WrapperPresenter<IMineLotteryRecordView> implements IMineLotteryRecordPresenter {
    private IMineLotteryRecordModel mModel;

    public MineLotteryRecordPresenter() {
        mModel = new MineLotteryRecordModel(this);
        attachModel(mModel);
    }

    @Override
    public void getMineLotteryRecordList() {
        mView.showLoading();
        mModel.getMineLotteryRecordList();
    }

    @Override
    public void onGetMineLotteryRecordList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowMineLotteryRecordListToView((List<MineLotteryRecordEntity>) serverResponse.getResult());
        } else {
            mView.onShowMineLotteryRecordListToView(new ArrayList<>(0));
        }
    }

    @Override
    public void getLottery(String id, String consignee, String mobile, String province, String city, String district, String address) {
        mView.showLoading();
        mModel.getLottery(id, consignee, mobile, province, city, district, address);
    }

    @Override
    public void onGetLottery(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowMineGetLotteryResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
