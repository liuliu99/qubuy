package com.qubuyer.bean.home;

import java.io.Serializable;

public class HomeBannerEntity implements Serializable {
    private int ad_id;
    private String ad_name;
    private String ad_link;
    private String ad_code_full;
    private Integer goods_id;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String ad_link) {
        this.ad_link = ad_link;
    }

    public String getAd_code_full() {
        return ad_code_full;
    }

    public void setAd_code_full(String ad_code_full) {
        this.ad_code_full = ad_code_full;
    }
}
