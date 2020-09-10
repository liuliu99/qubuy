package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IUpdateNicknamePresenter {
    void updateNickname(String nickname);
    void onUpdateNickName(ServerResponse serverResponse);
}
