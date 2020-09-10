package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.IWalletPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class WalletModel implements IWalletModel {

    private IWalletPresenter mPresenter;

    public WalletModel(IWalletPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getWalletInfo() {
        HttpInvoker.createBuilder(NetConstant.GET_WALLET_INFO_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(WalletInfoEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetWalletInfo(serverResponse);
            }
        });
    }
}
