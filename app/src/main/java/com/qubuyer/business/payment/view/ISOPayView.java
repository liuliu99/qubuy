package com.qubuyer.business.payment.view;

import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.UserWalletEntity;
import com.qubuyer.bean.payment.AlipayParamEntity;
import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.bean.payment.PayResultEntity;
import com.qubuyer.bean.payment.WechatPayParamEntity;

import java.util.List;

public interface ISOPayView extends BaseView {
    void onShowPayListDataToView(List<PayListEntity> list);

    void onShowWechatParamToView(WechatPayParamEntity entity);

    void onShowAlipayParamToView(String result);

    void onShowPayResultToView(boolean isSuccess, String resultString, PayResultEntity entity);

    void onShowUserWalletToView(UserWalletEntity entity);

    void destory();
}
