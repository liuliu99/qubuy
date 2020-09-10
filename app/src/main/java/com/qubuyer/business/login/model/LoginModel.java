package com.qubuyer.business.login.model;

import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.business.login.presenter.ILoginPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginModel implements ILoginModel {

    private ILoginPresenter mPresenter;

    public LoginModel(ILoginPresenter loginPresenter) {
        mPresenter = loginPresenter;
    }

    @Override
    public void login(String phone, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", password);
        HttpInvoker.createBuilder(NetConstant.LOGIN_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(LoginEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLogin(serverResponse);
            }
        });
    }

    @Override
    public void wecahtOrQQLogin(String oauth, String openid) {
        Map<String, String> params = new HashMap<>();
        params.put("oauth", oauth);
        params.put("openid", openid);
        HttpInvoker.createBuilder(NetConstant.WECHAT_OR_QQ_LOGIN_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(ThirdLoginEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onWechatOrQQLogin(serverResponse);
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
    public void loginWithPhoneCode(String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("code", code);
        HttpInvoker.createBuilder(NetConstant.LOGIN_PHONE_CODE_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(LoginEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onLogin(serverResponse);
            }
        });
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }
}
