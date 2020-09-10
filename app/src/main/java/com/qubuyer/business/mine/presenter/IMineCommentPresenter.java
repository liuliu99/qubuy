package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineCommentPresenter {
    void getMineCommentList();
    void onGetMineCommmentList(ServerResponse serverResponse);
}
