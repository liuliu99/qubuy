package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.presenter.IMessageDetailPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class MessageDetailModel implements IMessageDetailModel {
    private IMessageDetailPresenter mMinePresenter;

    public MessageDetailModel(IMessageDetailPresenter minePresenter) {
        this.mMinePresenter = minePresenter;
    }

    @Override
    public void destroy() {
        mMinePresenter = null;
    }

    @Override
    public void getMessageDetailInfo(int messgeId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", messgeId + "");
        HttpInvoker.createBuilder(NetConstant.GET_ACTIVITY_MESSAGE_DETAIL_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mMinePresenter) return;
                mMinePresenter.onGetMessageDeatilInfo(serverResponse);
            }
        });
    }
}
