package com.qubuyer.business.register.model;

import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.presenter.IBindPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class BindModel implements IBindModel {

    private IBindPresenter mPresenter;

    public BindModel(IBindPresenter loginPresenter) {
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
    public void bindPhone(String mobile, String code, String openid, String oauth) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("openid", openid);
        params.put("oauth", oauth);
        HttpInvoker.createBuilder(NetConstant.WECHAT_OR_QQ_BIND_PHONE_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(RegisterEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onBindPhone(serverResponse);
            }
        });
    }
}
