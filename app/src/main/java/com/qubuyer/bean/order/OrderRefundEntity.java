package com.qubuyer.bean.order;

import com.qubuyer.bean.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/4/1
 * @description 退款实体
 * @version
 */
public class OrderRefundEntity extends Entity {
    /**
     * id : 100
     * rec_id : 981
     * split_id : 885
     * order_id : 1031
     * order_sn : 201904171758509512
     * goods_id : 74
     * goods_num : 1
     * user_id : 292
     * goods_name : 广西移动 手机 话费充值 50元 快充直充 24小时自动充值快速到账
     * original_img_full : http://api.qubuyer.com//public/upload/goods/2019/04-02/1adae1e8699b6e31b3bbb1d961452b52.jpg
     * status_explain : 不同意
     */

    private int id;
    private int rec_id;
    private int split_id;
    private int order_id;
    private String order_sn;
    private int goods_id;
    private int goods_num;
    private int user_id;
    private String goods_name;
    private String shop_price;
    private String original_img_full;
    private String status_explain;

    private String describe;
    private String spec_key_name;
    private String refund_money;
    private String add_time_format;
    private StatusDescBean status_desc;
    private List<String> imgs_full;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public int getSplit_id() {
        return split_id;
    }

    public void setSplit_id(int split_id) {
        this.split_id = split_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOriginal_img_full() {
        return original_img_full;
    }

    public void setOriginal_img_full(String original_img_full) {
        this.original_img_full = original_img_full;
    }

    public String getStatus_explain() {
        return status_explain;
    }

    public void setStatus_explain(String status_explain) {
        this.status_explain = status_explain;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(String refund_money) {
        this.refund_money = refund_money;
    }

    public String getAdd_time_format() {
        return add_time_format;
    }

    public void setAdd_time_format(String add_time_format) {
        this.add_time_format = add_time_format;
    }

    public StatusDescBean getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(StatusDescBean status_desc) {
        this.status_desc = status_desc;
    }

    public List<String> getImgs_full() {
        return imgs_full;
    }

    public void setImgs_full(List<String> imgs_full) {
        this.imgs_full = imgs_full;
    }

    public static class StatusDescBean implements Serializable {
        /**
         * title : 请等待商家处理
         * desc : 您已成功发起了退款申请，请耐心等待商家处理
         * note : 如商家拒绝，您可以修改退款申请后再次发起，商家会重新处理
         */

        private String title;
        private String desc;
        private String note;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }
}
