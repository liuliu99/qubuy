package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.IWalletPresenter;
import com.qubuyer.business.mine.presenter.IWithdrawPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class WithdrawModel implements IWithdrawModel {
    private IWithdrawPresenter mPresenter;

    public WithdrawModel(IWithdrawPresenter presenter) {
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
    public void getVerificationcode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        HttpInvoker.createBuilder(NetConstant.GET_FIND_PWD_VERIFICATION_CODE_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetVerificationcode(serverResponse);
            }
        });
    }

    @Override
    public void withdraw(String money, String oauth, String mobile, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("money", money);
        params.put("oauth", oauth);
        params.put("mobile", mobile);
        params.put("code", code);
        HttpInvoker.createBuilder(NetConstant.WITHDRAW_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onWithdraw(serverResponse);
            }
        });
    }
}
