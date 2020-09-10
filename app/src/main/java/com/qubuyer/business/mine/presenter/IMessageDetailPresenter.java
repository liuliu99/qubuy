package com.qubuyer.business.mine.presenter;


import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMessageDetailPresenter {
    void getMessageDetailInfo(int messgeId);
    void onGetMessageDeatilInfo(ServerResponse serverResponse);
}
