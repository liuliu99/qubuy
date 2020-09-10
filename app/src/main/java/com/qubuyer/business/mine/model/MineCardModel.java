package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.MinePosterEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.presenter.IMineCardPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class MineCardModel implements IMineCardModel {
    private IMineCardPresenter mPresenter;

    public MineCardModel(IMineCardPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getQrCode() {
        HttpInvoker.createBuilder(NetConstant.GET_QR_CODE_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineCardEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetQrCode(serverResponse);
            }
        });
    }

    @Override
    public void getPoster() {
        HttpInvoker.createBuilder(NetConstant.GET_POSTER_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MinePosterEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetPoster(serverResponse);
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
