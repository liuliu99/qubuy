package com.qubuyer.business.payment.model;

import com.qubuyer.base.mvp.BaseModel;

/**
 * @date 创建时间:2019/3/15
 * @author Susong
 * @description 订单支付
 & @version
 */
public interface ISOPayModel extends BaseModel {
    void loadPayListData();
    /**
     * 获取微信支付参数
     * @param orderNo 支付单ID
     */
    void getWechatPayParam(String orderNo);
    /**
     * 获取支付宝支付参数
     * @param orderNo 支付单ID
     */
    void getAlipayParam(String orderNo);

    /**
     * 获取订单支付结果
     * @param orderNo 订单号
     */
    void getOrderPayResult(String orderNo);

    /**
     * 获取用户钱包信息
     */
    void getUserWallet();
}
