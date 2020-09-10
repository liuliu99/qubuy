package com.qubuyer.bean.payment;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/3/29
 * @author Susong
 * @description 计算订单金额结果实体类
 & @version
 */
public class CalcOrderPriceResultEntity extends Entity {
    //邮费
    private float shipping_price;
    //订单应该付金额(主要用这个)
    private float order_amount;
    //订单总金额
    private float total_amount;
    //商品金额
    private float goods_price;
    //商品总共数量
    private float total_num;
    //优惠金额
    private float order_cut_price;

    private String msg1;
    private String msg2;

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public float getOrder_cut_price() {
        return order_cut_price;
    }

    public void setOrder_cut_price(float order_cut_price) {
        this.order_cut_price = order_cut_price;
    }

    public float getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(float shipping_price) {
        this.shipping_price = shipping_price;
    }

    public float getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(float order_amount) {
        this.order_amount = order_amount;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public float getTotal_num() {
        return total_num;
    }

    public void setTotal_num(float total_num) {
        this.total_num = total_num;
    }
}
