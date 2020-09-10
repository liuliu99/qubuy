package com.qubuyer.bean.splash;

import com.qubuyer.bean.Entity;

public class SplashEntity extends Entity {
    private Integer goods_id;
    private String ad_code_full;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getAd_code_full() {
        return ad_code_full;
    }

    public void setAd_code_full(String ad_code_full) {
        this.ad_code_full = ad_code_full;
    }
}
