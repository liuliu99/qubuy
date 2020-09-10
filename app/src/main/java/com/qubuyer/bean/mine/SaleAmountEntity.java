package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 销售总额实体类
 * @version
 */
public class SaleAmountEntity extends Entity {

    /**
     * list : [{"id":27,"user_id":270,"distribut_money":3078,"order":{"id":700,"order_sn":"201903141635561906","order_goods":[{"goods_name":"小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ","goods_price":"2598.00","rec_id":703,"goods_id":5,"goods_num":10},{"goods_name":"哥弟女装2018春季新款口袋趣味图案贴标连帽针织长开衫A400065 连帽设计 实穿美观","goods_price":"480.00","rec_id":704,"goods_id":7,"goods_num":10}]}},{"id":28,"user_id":270,"distribut_money":3078,"order":{"id":700,"order_sn":"201903141635561906","order_goods":[{"goods_name":"小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ","goods_price":"2598.00","rec_id":703,"goods_id":5,"goods_num":10},{"goods_name":"哥弟女装2018春季新款口袋趣味图案贴标连帽针织长开衫A400065 连帽设计 实穿美观","goods_price":"480.00","rec_id":704,"goods_id":7,"goods_num":10}]}},{"id":29,"user_id":270,"distribut_money":3078,"order":{"id":700,"order_sn":"201903141635561906","order_goods":[{"goods_name":"小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ","goods_price":"2598.00","rec_id":703,"goods_id":5,"goods_num":10},{"goods_name":"哥弟女装2018春季新款口袋趣味图案贴标连帽针织长开衫A400065 连帽设计 实穿美观","goods_price":"480.00","rec_id":704,"goods_id":7,"goods_num":10}]}}]
     * total_money : 9234
     */

    private float total_money;
    private List<ListBean> list;

    public float getTotal_money() {
        return total_money;
    }

    public void setTotal_money(float total_money) {
        this.total_money = total_money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 27
         * user_id : 270
         * distribut_money : 3078
         * order : {"id":700,"order_sn":"201903141635561906","order_goods":[{"goods_name":"小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ","goods_price":"2598.00","rec_id":703,"goods_id":5,"goods_num":10},{"goods_name":"哥弟女装2018春季新款口袋趣味图案贴标连帽针织长开衫A400065 连帽设计 实穿美观","goods_price":"480.00","rec_id":704,"goods_id":7,"goods_num":10}]}
         */

        private int id;
        private int user_id;
        private float distribut_money;
        private OrderBean order;

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

        public float getDistribut_money() {
            return distribut_money;
        }

        public void setDistribut_money(float distribut_money) {
            this.distribut_money = distribut_money;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean implements Serializable {
            /**
             * id : 700
             * order_sn : 201903141635561906
             * order_goods : [{"goods_name":"小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ","goods_price":"2598.00","rec_id":703,"goods_id":5,"goods_num":10},{"goods_name":"哥弟女装2018春季新款口袋趣味图案贴标连帽针织长开衫A400065 连帽设计 实穿美观","goods_price":"480.00","rec_id":704,"goods_id":7,"goods_num":10}]
             */

            private int id;
            private String order_sn;
            private List<OrderGoodsBean> order_goods;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public List<OrderGoodsBean> getOrder_goods() {
                return order_goods;
            }

            public void setOrder_goods(List<OrderGoodsBean> order_goods) {
                this.order_goods = order_goods;
            }

            public static class OrderGoodsBean implements Serializable {
                /**
                 * goods_name : 小米（MI）电视 55英寸 4K 智能WiFi网络平板 智能语音 液晶电视机 标准版 T 4A L55M5-AZ
                 * goods_price : 2598.00
                 * rec_id : 703
                 * goods_id : 5
                 * goods_num : 10
                 */

                private String goods_name;
                private String goods_price;
                private int rec_id;
                private int goods_id;
                private int goods_num;

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public int getRec_id() {
                    return rec_id;
                }

                public void setRec_id(int rec_id) {
                    this.rec_id = rec_id;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public int getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(int goods_num) {
                    this.goods_num = goods_num;
                }
            }
        }
    }
}
