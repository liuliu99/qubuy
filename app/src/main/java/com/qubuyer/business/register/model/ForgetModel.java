package com.qubuyer.business.register.model;

import com.qubuyer.business.register.presenter.IForgetPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class ForgetModel implements IForgetModel {

    private IForgetPresenter mPresenter;

    public ForgetModel(IForgetPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
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
    public void findPwd(String mobile, String password, String code, String password2) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("code", code);
        params.put("password2", password2);
        HttpInvoker.createBuilder(NetConstant.FIND_PWD_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onFindPwd(serverResponse);
            }
        });
    }
}
