package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 销售总额实体类
 * @version
 */
public class RebateOrderEntity extends Entity {

    private int rec_id;
    private int goods_id;
    private String goods_name;
    private String order_sn;
    private String expect_price;
    private double total_amount;
    private String add_time;
    private String spec_key_name;
    private int goods_num;
    private String member_goods_price;
    private int restore_end;
    private String original_img_full;
    private String current_price;
    private float current_radio;
    private float current_month_radio;
    private float total_price;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getExpect_price() {
        return expect_price;
    }

    public void setExpect_price(String expect_price) {
        this.expect_price = expect_price;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getMember_goods_price() {
        return member_goods_price;
    }

    public void setMember_goods_price(String member_goods_price) {
        this.member_goods_price = member_goods_price;
    }

    public int getRestore_end() {
        return restore_end;
    }

    public void setRestore_end(int restore_end) {
        this.restore_end = restore_end;
    }

    public String getOriginal_img_full() {
        return original_img_full;
    }

    public void setOriginal_img_full(String original_img_full) {
        this.original_img_full = original_img_full;
    }

    public String getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(String current_price) {
        this.current_price = current_price;
    }

    public float getCurrent_radio() {
        return current_radio;
    }

    public void setCurrent_radio(float current_radio) {
        this.current_radio = current_radio;
    }

    public float getCurrent_month_radio() {
        return current_month_radio;
    }

    public void setCurrent_month_radio(float current_month_radio) {
        this.current_month_radio = current_month_radio;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }
}
