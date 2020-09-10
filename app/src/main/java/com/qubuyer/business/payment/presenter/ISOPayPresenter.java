package com.qubuyer.business.payment.presenter;

import com.qubuyer.bean.payment.PayListEntity;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.List;

public interface ISOPayPresenter {
    void loadPayListData();
    void onLoadPayListData(List<PayListEntity> entity);
    /**
     * 获取微信支付参数
     * @param orderNo 支付单ID
     */
    void getWechatPayParam(String orderNo);
    void onGetWechatPayParam(ServerResponse serverResponse);

    /**
     * 获取支付宝支付参数
     * @param orderNo 支付单ID
     */
    void getAlipayParam(String orderNo);
    void onGetAlipayParam(ServerResponse serverResponse);

    /**
     * 获取订单支付结果
     * @param orderNo 订单号
     */
    void getOrderPayResult(String orderNo);
    void onGetOrderPayResult(ServerResponse serverResponse);

    /**
     * 获取用户钱包信息
     */
    void getUserWallet();
    void onGetUserWallet(ServerResponse serverResponse);
}
