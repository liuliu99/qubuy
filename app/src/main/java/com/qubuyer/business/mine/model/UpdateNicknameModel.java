package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.IUpdateNicknamePresenter;
import com.qubuyer.business.mine.presenter.IWalletPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class UpdateNicknameModel implements IUpdateNicknameModel {

    private IUpdateNicknamePresenter mPresenter;

    public UpdateNicknameModel(IUpdateNicknamePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void updateNickname(String nickname) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", nickname);
        HttpInvoker.createBuilder(NetConstant.UPDATE_USER_NAME_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(map)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onUpdateNickName(serverResponse);
            }
        });
    }
}
