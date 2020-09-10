package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineCommentEntity;
import com.qubuyer.business.mine.model.IMineCommentModel;
import com.qubuyer.business.mine.model.MineCommentModel;
import com.qubuyer.business.mine.view.IMineCommentView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class MineCommentPresenter extends WrapperPresenter<IMineCommentView> implements IMineCommentPresenter {
    private IMineCommentModel mModel;

    public MineCommentPresenter() {
        mModel = new MineCommentModel(this);
        attachModel(mModel);
    }

    @Override
    public void getMineCommentList() {
        mView.showLoading();
        mModel.getMineCommentList();
    }

    @Override
    public void onGetMineCommmentList(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowMineCommentListToView((List<MineCommentEntity>) serverResponse.getResult());
        } else {
            mView.onShowMineCommentListToView(new ArrayList<>(0));
        }
    }
}
