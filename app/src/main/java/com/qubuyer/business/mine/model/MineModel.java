package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.IMinePresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class MineModel implements IMineModel {

    private IMinePresenter mPresenter;

    public MineModel(IMinePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getUserInfo() {
        HttpInvoker.createBuilder(NetConstant.GET_USER_INFO_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(UserEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetUserInfo(serverResponse);
            }
        });
    }

    @Override
    public void getUserMessageCount() {
        HttpInvoker.createBuilder(NetConstant.GET_USER_MESSAGE_COUNT_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(UserMessageCountEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetUserMessageCount(serverResponse);
            }
        });
    }

    @Override
    public void loginOut() {
        HttpInvoker.createBuilder(NetConstant.LOGIN_OUT_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLoginOut(serverResponse);
            }
        });
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
