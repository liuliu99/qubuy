package com.qubuyer.bean.good;

import java.io.Serializable;

/**
 * @date 创建时间:2019/3/25
 * @author Susong
 * @description 商品评论实体
 & @version
 */
public class GoodAssessEntity implements Serializable {

    /**
     * img_sum : 0
     * high_sum : 1
     * center_sum : 0
     * low_sum : 0
     * total_sum : 1
     * high_rate : 100
     * center_rate : 0
     * low_rate : 0
     */

    private String img_sum;
    private String high_sum;
    private String center_sum;
    private String low_sum;
    private int total_sum;
    private int high_rate;
    private int center_rate;
    private int low_rate;

    public String getImg_sum() {
        return img_sum;
    }

    public void setImg_sum(String img_sum) {
        this.img_sum = img_sum;
    }

    public String getHigh_sum() {
        return high_sum;
    }

    public void setHigh_sum(String high_sum) {
        this.high_sum = high_sum;
    }

    public String getCenter_sum() {
        return center_sum;
    }

    public void setCenter_sum(String center_sum) {
        this.center_sum = center_sum;
    }

    public String getLow_sum() {
        return low_sum;
    }

    public void setLow_sum(String low_sum) {
        this.low_sum = low_sum;
    }

    public int getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(int total_sum) {
        this.total_sum = total_sum;
    }

    public int getHigh_rate() {
        return high_rate;
    }

    public void setHigh_rate(int high_rate) {
        this.high_rate = high_rate;
    }

    public int getCenter_rate() {
        return center_rate;
    }

    public void setCenter_rate(int center_rate) {
        this.center_rate = center_rate;
    }

    public int getLow_rate() {
        return low_rate;
    }

    public void setLow_rate(int low_rate) {
        this.low_rate = low_rate;
    }
}
