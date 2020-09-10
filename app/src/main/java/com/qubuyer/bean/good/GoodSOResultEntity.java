package com.qubuyer.bean.good;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2019/3/29
 * @description 确认订单页下单结果实体列
 * @version
 */
public class GoodSOResultEntity extends Entity {

    /**
     * order_sn : 201903261714364231
     * order_amount : 120.00
     * order_id : 762
     */

    private String order_sn;
    private String order_amount;
    private String order_id;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
