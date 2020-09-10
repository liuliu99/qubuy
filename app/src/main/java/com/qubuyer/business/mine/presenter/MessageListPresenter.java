package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.model.IMessageListModel;
import com.qubuyer.business.mine.model.MessageListModel;
import com.qubuyer.business.mine.view.IMessageListPageView;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;


public class MessageListPresenter extends WrapperPresenter<IMessageListPageView> implements IMessageListPresenter {
    private IMessageListModel mModel;

    public MessageListPresenter() {
        mModel = new MessageListModel(this);
        attachModel(mModel);
    }

    @Override
    public void loadMore(int type) {
        mModel.getMessageList(AppConstant.LOAD_TYPE_UP, type);
    }

    @Override
    public void refresh(int type) {
        mModel.getMessageList(AppConstant.LOAD_TYPE_DOWN, type);
    }

    @Override
    public void onLoadDataResult(int loadType, ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            List<MessageEntity> list = (List<MessageEntity>) serverResponse.getResult();
            switch (loadType) {
                case AppConstant.LOAD_TYPE_UP:
                    if (isViewNotNull()) {
                        mView.finishLoadMore(0, true, null == list || list.size() < AppConstant.COUNT_SMAll);
//                        mView.onShowLoadMoreListToView(list);
                    }
                    break;
                default:
                    if (loadType == AppConstant.LOAD_TYPE_DOWN && isViewNotNull()) {
                        mView.finishRefresh(true);
                    }
                    if (isViewNotNull()) {
                        mView.onShowRefreshListToView(list);
                    }
                    break;
            }
        } else {
            if (isViewNotNull()) {
                switch (loadType) {
                    case AppConstant.LOAD_TYPE_UP:
                        mView.finishLoadMore(0, false, true);
                        break;
                    case AppConstant.LOAD_TYPE_DOWN:
                        mView.finishRefresh(false);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
