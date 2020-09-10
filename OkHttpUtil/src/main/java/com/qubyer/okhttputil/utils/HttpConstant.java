package com.qubyer.okhttputil.utils;

/**
 * HTTP相关常量
 *
 * @author jiangtianming
 * @date 2016/7/4 9:48
 */
public interface HttpConstant {
    /**
     * 请求状态
     * PS：四位数9开头是本地错误码
     */
    interface Status {
        /**
         * 其他错误
         */
        int LOCAL_STATUS_OTHERS = 9000;
        /**
         * 请求失败
         */
        int LOCAL_STATUS_REQUEST_FAIL = 400;
        /**
         * 解析json失败
         */
        int LOCAL_STATUS_FROM_JSON_TO_BEAN_FAIL = 9002;

        /**
         * 请求成功
         */
        int SERVER_STATUS_SUCCESSFUL = 200;
        /**
         * token过期
         */
        int SERVER_STATUS_TOKEN_OVERDUED = -10;

        int CODE_LOGIN_OVERTIME = 1002;  //登录过期
        int CODE_LOGIN = 1003;  //请登录
        int CODE_TOKEN_NO_EXIST = 1004;  //token不存在
        int CODE_TOKEN_OVERTIME = 1005;  //token已过期
        int CODE_TOKEN_INFO_UNUSUAL = 1006;  //token信息不正确
        int CODE_ACCOUNT_NO_EXIST = 1007;  //账号不存在
    }
}
