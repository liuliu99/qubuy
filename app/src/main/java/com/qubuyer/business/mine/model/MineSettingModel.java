package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.mine.presenter.IMinePresenter;
import com.qubuyer.business.mine.presenter.IMineSettingPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MineSettingModel implements IMineSettingModel {
    private IMineSettingPresenter mPresenter;

    public MineSettingModel(IMineSettingPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
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
    public void updateHeadImg(File file) {
        Map<String, File> fileParams = new HashMap<>();
        fileParams.put("head", file);
        HttpInvoker.createBuilder(NetConstant.UPDATE_USER_HEAD_IMG_URL)
                .setFileParams(fileParams)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_FILEUP)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onUpdateHeadImg(serverResponse);
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
}
