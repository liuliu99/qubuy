package com.qubuyer.bean.shopcart;

import com.qubuyer.bean.home.HomeGoodEntity;

import java.io.Serializable;

/**
 * @author Susong
 * @date 创建时间2019/3/21
 * @description 购物车商品实体
 * @version
 */
public class ShopCartGoodEntity implements Serializable {

    /**
     * id : 4
     * user_id : 160
     * session_id : i6gkg5s21pes4otmrnd164atb6
     * goods_id : 5
     * goods_sn : TP0000005
     * goods_name : 小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ
     * market_price : 3099.00
     * goods_price : 2598.00
     * member_goods_price : 2598.00
     * goods_num : 10
     * item_id : 0
     * spec_key :
     * spec_key_name :
     * bar_code :
     * selected : 1
     * add_time : 1551427589
     * prom_type : 0
     * prom_id : 0
     * sku :
     * combination_group_id : 0
     * cat_id : 354
     * store_count : 100
     * is_on_sale : 1
     * weight : 16300
     * goods : {"goods_id":5,"cat_id":354,"store_count":100,"is_on_sale":1,"prom_type":0,"prom_id":0,"weight":16300,"is_virtual":0}
     */

    private int id;
    private int user_id;
    private String session_id;
    private int goods_id;
    private String goods_sn;
    private String goods_name;
    private String market_price;
    private String goods_price;
    private String member_goods_price;
    private int goods_num;
    private int item_id;
    private String spec_key;
    private String spec_key_name;
    private String bar_code;
    private int selected;
    private int add_time;
    private int prom_type;
    private int prom_id;
    private String sku;
    private int combination_group_id;
    private int cat_id;
    private int store_count;
    private int is_on_sale;
    private int weight;
    private HomeGoodEntity goods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getMember_goods_price() {
        return member_goods_price;
    }

    public void setMember_goods_price(String member_goods_price) {
        this.member_goods_price = member_goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getSpec_key() {
        return spec_key;
    }

    public void setSpec_key(String spec_key) {
        this.spec_key = spec_key;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getProm_type() {
        return prom_type;
    }

    public void setProm_type(int prom_type) {
        this.prom_type = prom_type;
    }

    public int getProm_id() {
        return prom_id;
    }

    public void setProm_id(int prom_id) {
        this.prom_id = prom_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCombination_group_id() {
        return combination_group_id;
    }

    public void setCombination_group_id(int combination_group_id) {
        this.combination_group_id = combination_group_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public int getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(int is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HomeGoodEntity getGoods() {
        return goods;
    }

    public void setGoods(HomeGoodEntity goods) {
        this.goods = goods;
    }
}
