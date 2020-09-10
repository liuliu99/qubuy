package com.qubuyer.bean.good;

import com.qubuyer.bean.Entity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/26
 * @description 提交订单商品价格信息实体
 * @version
 */
public class SOGoodInfoEntity extends Entity {
    private List<ShopCartGoodEntity> cartList;
    private PriceInfo cartPriceInfo;
    private float cartGoodsTotalNum;

    public List<ShopCartGoodEntity> getCartList() {
        return cartList;
    }

    public void setCartList(List<ShopCartGoodEntity> cartList) {
        this.cartList = cartList;
    }

    public PriceInfo getCartPriceInfo() {
        return cartPriceInfo;
    }

    public void setCartPriceInfo(PriceInfo cartPriceInfo) {
        this.cartPriceInfo = cartPriceInfo;
    }

    public float getCartGoodsTotalNum() {
        return cartGoodsTotalNum;
    }

    public void setCartGoodsTotalNum(float cartGoodsTotalNum) {
        this.cartGoodsTotalNum = cartGoodsTotalNum;
    }

    public class PriceInfo{

        /**
         * total_fee : 30150
         * goods_fee : 0
         * goods_num : 40
         */

        private float total_fee;
        private float goods_fee;
        private float goods_num;

        public float getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(float total_fee) {
            this.total_fee = total_fee;
        }

        public float getGoods_fee() {
            return goods_fee;
        }

        public void setGoods_fee(float goods_fee) {
            this.goods_fee = goods_fee;
        }

        public float getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(float goods_num) {
            this.goods_num = goods_num;
        }
    }

}
