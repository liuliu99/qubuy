package com.qubuyer.bean.payment;

import java.io.Serializable;

/**
 * @date 创建时间:2019/3/15
 * @author Susong
 * @description 支付宝支付参数实体
 & @version
 */
public class AlipayParamEntity implements Serializable {
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
