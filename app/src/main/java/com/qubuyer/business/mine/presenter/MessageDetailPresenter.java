package com.qubuyer.business.mine.presenter;


import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.model.IMessageDetailModel;
import com.qubuyer.business.mine.model.MessageDetailModel;
import com.qubuyer.business.mine.view.IMessageDetailView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class MessageDetailPresenter extends WrapperPresenter<IMessageDetailView> implements IMessageDetailPresenter {
    private IMessageDetailModel model;

    public MessageDetailPresenter() {
        model = new MessageDetailModel(this);
        attachModel(model);
    }

    @Override
    public void getMessageDetailInfo(int messgeId) {
        mView.showLoading();
        model.getMessageDetailInfo(messgeId);
    }

    @Override
    public void onGetMessageDeatilInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowMessageDetailToView((String) serverResponse.getResult());
        } else {
            mView.onShowMessageDetailToView(null);
        }
    }
}
