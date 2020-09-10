package com.qubyer.okhttputil.helper;

import com.qubyer.okhttputil.callback.OnTokenOverduedListener;
import com.qubyer.okhttputil.utils.HttpConstant;

public class HttpManager {
    private HttpManager() {
    }

    private static HttpManager instance;

    public static HttpManager getInstance() {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new HttpManager();
            }
        }
        return instance;
    }

    private String token;

    private OnTokenOverduedListener tokenOverduedListener;

    public void init(String token, OnTokenOverduedListener tokenOverduedListener){
        this.token = token;
        this.tokenOverduedListener = tokenOverduedListener;
    }

    /**
     * 执行token回调
     */
    private synchronized void doOnTokenOverduedListener(){
        if (tokenOverduedListener != null){
            tokenOverduedListener.onTokenOverdued();
            this.token = null;
            this.tokenOverduedListener = null;
        }
    }

    /**
     * 执行http过滤
     * @param serverResponse
     */
    public void doHttpFilter(ServerResponse serverResponse){
        switch (serverResponse.getCode()){
            case HttpConstant.Status.SERVER_STATUS_TOKEN_OVERDUED:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_LOGIN_OVERTIME:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_LOGIN:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_TOKEN_NO_EXIST:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_TOKEN_OVERTIME:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_TOKEN_INFO_UNUSUAL:
                doOnTokenOverduedListener();
                break;
            case HttpConstant.Status.CODE_ACCOUNT_NO_EXIST:
                doOnTokenOverduedListener();
                break;
        }
    }

    public String getToken(){
        return token;
    }
}
