package com.qubuyer.business.mine.model;

import com.qubuyer.bean.mine.MineCommentEntity;
import com.qubuyer.business.mine.presenter.IMineCommentPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.Map;

public class MineCommentModel implements IMineCommentModel {
    private IMineCommentPresenter mPresenter;

    public MineCommentModel(IMineCommentPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getMineCommentList() {
        HttpInvoker.createBuilder(NetConstant.GET_MINE_COMMMENT_LIST_POST)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineCommentEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetMineCommmentList(serverResponse);
            }
        });
    }
}
