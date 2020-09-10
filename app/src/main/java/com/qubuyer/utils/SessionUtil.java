package com.qubuyer.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.blankj.utilcode.util.SPUtils;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.callback.OnTokenOverduedListener;

/**
 * @author Susong
 * @date 创建时间2019/3/10
 * @description 登录状态管理
 */
public class SessionUtil {
    private static SessionUtil instance;
    //登录用户信息
    private UserEntity userEntity;

    public static SessionUtil getInstance() {
        synchronized (SessionUtil.class) {
            if (instance == null) {
                instance = new SessionUtil();
            }
        }
        return instance;
    }

    public synchronized String getToken() {
        return SPUtils.getInstance().getString(AppConstant.SPF_KEY_LOGIN_TOKEN);
    }

    public synchronized void setToken(String token) {
        SPUtils.getInstance().put(AppConstant.SPF_KEY_LOGIN_TOKEN, token);
    }

    public synchronized boolean isLogined() {
        String toekn = SPUtils.getInstance().getString(AppConstant.SPF_KEY_LOGIN_TOKEN);
        return null != toekn && !TextUtils.isEmpty(toekn);
    }

    public synchronized void loginOut() {
        SPUtils.getInstance().remove(AppConstant.SPF_KEY_LOGIN_TOKEN);
        clearUserInfo();
    }

    public synchronized void saveUserInfo(UserEntity entitiy) {
        userEntity = entitiy;
        CacheDoubleUtils.getInstance().put(AppConstant.LOGIN_USER_INFO, entitiy);
    }

    public synchronized void clearUserInfo() {
        CacheDoubleUtils.getInstance().remove(AppConstant.LOGIN_USER_INFO);
        userEntity = null;
    }

    public synchronized UserEntity getUserInfo() {
        if (null == userEntity) {
            userEntity = (UserEntity) CacheDoubleUtils.getInstance().getSerializable(AppConstant.LOGIN_USER_INFO);
        }
        return userEntity;
    }

    private OnTokenOverduedListener mTokenOverduedListener = new OnTokenOverduedListener() {

        @Override
        public void onTokenOverdued() {
            SessionUtil.getInstance().loginOut();
//            ActivityUtils.finishAllActivities();
//            NavigationUtil.overlay(ActivityUtils.getTopActivity(), Login1Activity.class);
        }
    };

    public OnTokenOverduedListener getTokenOverduedListener() {
        return mTokenOverduedListener;
    }
}
