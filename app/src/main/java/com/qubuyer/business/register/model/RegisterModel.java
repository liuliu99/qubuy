package com.qubuyer.business.register.model;

import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.presenter.IRegisterPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class RegisterModel implements IRegisterModel {

    private IRegisterPresenter mPresenter;

    public RegisterModel(IRegisterPresenter loginPresenter) {
        mPresenter = loginPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getVerificationcode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        HttpInvoker.createBuilder(NetConstant.GET_REGISTER_VERIFICATION_CODE_POST)
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
    public void register(String username, String password, String mobile_code, String invitation_code) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("mobile_code", mobile_code);
        params.put("invitation_code", invitation_code);
        HttpInvoker.createBuilder(NetConstant.REGISTER_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(RegisterEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onRegister(serverResponse);
            }
        });
    }
}
