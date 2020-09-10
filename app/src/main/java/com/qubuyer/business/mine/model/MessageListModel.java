package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.presenter.IMessageListPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class MessageListModel implements IMessageListModel {
    private IMessageListPresenter mPresenter;
    private int pageNo = 1;

    public MessageListModel(IMessageListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getMessageList(int loadType, int type) {
        if (loadType == AppConstant.LOAD_TYPE_UP) {
            pageNo++;
        } else {
            pageNo = 1;
        }
        Map<String, String> params = new HashMap<>();
//        params.put("type", type + "");
        params.put("page", pageNo + "");
        HttpInvoker.createBuilder(type == 1 ? NetConstant.GET_ACTIVITY_MESSAGE_LIST_URL : NetConstant.GET_SYSTEM_MESSAGE_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MessageEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoadDataResult(loadType, serverResponse);
            }
        });
    }
}
