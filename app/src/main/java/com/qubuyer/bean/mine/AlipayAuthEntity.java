package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/4/12
 * @author Susong
 * @description 支付宝授权链接实体
 & @version
 */
public class AlipayAuthEntity extends Entity {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
