package com.qubuyer.business.register.model;

import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.presenter.ISetPwdPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class SetPwdModel implements ISetPwdModel {

    private ISetPwdPresenter mPresenter;

    public SetPwdModel(ISetPwdPresenter loginPresenter) {
        mPresenter = loginPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void setPwd(String token, String pushId, String password, String password2) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pushId", pushId);
        params.put("password", password);
        params.put("password2", password2);
        HttpInvoker.createBuilder(NetConstant.WECHAT_OR_QQ_LOGIN_SET_PWD_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(RegisterEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onSetPwd(serverResponse);
            }
        });
    }
}
