package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

import java.io.Serializable;

/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 我的收藏实体
 * @version
 */
public class MineCollectEntity extends Entity {

    /**
     * collect_id : 73
     * user_id : 269
     * restrore_price : 0.00
     * shop_price : 118.00
     * goods : {"goods_id":3,"goods_name":"佐露絲RALOS实拍大码女装连衣裙春装200斤胖妹妹遮肚子减龄胖mm蕾丝拼接裙子"}
     */

    private int collect_id;
    private int user_id;
    private String restrore_price;
    private String shop_price;
    private GoodsBean goods;

    public int getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(int collect_id) {
        this.collect_id = collect_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRestrore_price() {
        return restrore_price;
    }

    public void setRestrore_price(String restrore_price) {
        this.restrore_price = restrore_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class GoodsBean implements Serializable {
        /**
         * goods_id : 3
         * goods_name : 佐露絲RALOS实拍大码女装连衣裙春装200斤胖妹妹遮肚子减龄胖mm蕾丝拼接裙子
         */

        private int goods_id;
        private String goods_name;
        private String original_img;

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
    }
}
