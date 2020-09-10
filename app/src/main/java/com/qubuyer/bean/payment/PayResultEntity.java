package com.qubuyer.bean.payment;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间:2019/3/19
 * @description 支付结果实体
 * & @version
 */
public class PayResultEntity extends Entity {
    //支付单类型 1=订单 2=充值
    private int type;
    //支付单类型ID type=1 订单ID, type=2 充值单ID
    private String relation_id;
    //实付金额（元）
    private String pay_money;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }
}
