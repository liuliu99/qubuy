package com.qubuyer.bean.order;

import com.qubuyer.bean.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间:2019/3/29
 * @description 订单列表实体类
 * & @version
 */
public class OrderEntity extends Entity {
    /**
     * 订单分组状态
     */
    //全部
    public static final String ORDER_GROUP_STATUS_ALL = "";
    //待付款
    public static final String ORDER_GROUP_STATUS_OBLIGATION = "pay";
    //待发货
    public static final String ORDER_GROUP_STATUS_WAIT_SEND = "unreceiving";
    //待收货
    public static final String ORDER_GROUP_STATUS_WAIT_RECEIVING = "receiving";
    //待评价
    public static final String ORDER_GROUP_STATUS_TO_BE_COMMENT = "comment";
    //已完成
    public static final String ORDER_GROUP_STATUS_FINISH = "complete";

    private int id;
    private int order_id;
    private String order_sn;
    private int user_id;
    private String goods_price;
    private String shipping_name;
    private String shipping_price;
    private String order_amount;
    private String total_amount;
    private long add_time;
    private int shipping_time;
    private int confirm_time;
    private String cancel_reason;
    private int is_pay;
    private int is_cancel;
    private int is_receiving;
    private int is_comment;
    private int is_return;
    private int is_complete;
    private String state_brief;
    private int is_delete;
    private int is_extend_time;
    private int is_invoice;
    private long time_out;
    private String consignee;
    private String full_address;
    private String pay_name;

    private int invoice_type;
    private int invoice_title;
    private String invoice_name;
    private String invoice_mobile;
    private String invoice_email;
    private String invoice_taxpayer;
    private String invoice_desc;

    private String mobile;
    private String user_note;
    private List<SplitBean.OrderGoodBean> order_goods;
    private List<SplitBean> split;

    private InvoiceInfo invoice_info;

    public InvoiceInfo getInvoice_info() {
        return invoice_info;
    }

    public void setInvoice_info(InvoiceInfo invoice_info) {
        this.invoice_info = invoice_info;
    }

    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getInvoice_email() {
        return invoice_email;
    }

    public void setInvoice_email(String invoice_email) {
        this.invoice_email = invoice_email;
    }

    public String getInvoice_mobile() {
        return invoice_mobile;
    }

    public void setInvoice_mobile(String invoice_mobile) {
        this.invoice_mobile = invoice_mobile;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public int getIs_extend_time() {
        return is_extend_time;
    }

    public void setIs_extend_time(int is_extend_time) {
        this.is_extend_time = is_extend_time;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }

    public int getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(int invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getInvoice_taxpayer() {
        return invoice_taxpayer;
    }

    public void setInvoice_taxpayer(String invoice_taxpayer) {
        this.invoice_taxpayer = invoice_taxpayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice_desc() {
        return invoice_desc;
    }

    public void setInvoice_desc(String invoice_desc) {
        this.invoice_desc = invoice_desc;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public long getTime_out() {
        return time_out;
    }

    public void setTime_out(long time_out) {
        this.time_out = time_out;
    }

    public List<SplitBean> getSplit() {
        return split;
    }

    public void setSplit(List<SplitBean> split) {
        this.split = split;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public int getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(int shipping_time) {
        this.shipping_time = shipping_time;
    }

    public int getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(int confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public int getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    public int getIs_receiving() {
        return is_receiving;
    }

    public void setIs_receiving(int is_receiving) {
        this.is_receiving = is_receiving;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public int getIs_return() {
        return is_return;
    }

    public void setIs_return(int is_return) {
        this.is_return = is_return;
    }

    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }

    public String getState_brief() {
        return state_brief;
    }

    public void setState_brief(String state_brief) {
        this.state_brief = state_brief;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public List<SplitBean.OrderGoodBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<SplitBean.OrderGoodBean> order_goods) {
        this.order_goods = order_goods;
    }

    public static class SplitBean implements Serializable {
        private int id;
        private int rec_id;
        private int order_id;
        private String order_sn;
        private int user_id;
        private int order_status;
        private int shipping_status;
        private int pay_status;
        private String consignee;
        private int country;
        private int province;
        private int city;
        private int district;
        private int twon;
        private int goods_id;
        private String address;
        private String zipcode;
        private String mobile;
        private String email;
        private String shipping_code;
        private String shipping_name;
        private String pay_code;
        private String pay_name;

        private int invoice_type;
        private int invoice_title;
        private String invoice_name;
        private String invoice_mobile;
        private String invoice_email;
        private String invoice_taxpayer;
        private String invoice_desc;

        private String goods_price;
        private String shipping_price;
        private String user_money;
        private String coupon_price;
        private int integral;
        private String integral_money;
        private String order_amount;
        private String total_amount;
        private int add_time;
        private int shipping_time;
        private int confirm_time;
        private int pay_time;
        private Object transaction_id;
        private int prom_id;
        private int prom_type;
        private int order_prom_id;
        private String order_prom_amount;
        private String discount;
        private String user_note;
        private String cancel_reason;
        private String admin_note;
        private int is_distribut;
        private String paid_money;
        private int shop_id;
        private int deleted;
        private int is_pay;
        private int is_cancel;
        private int is_receiving;
        private int is_comment;
        private int is_return;
        private int is_complete;
        private String state_brief;
        private int is_delete;
        private int is_extend_time;
        private int is_invoice;
        private String goods_name;
        private String goods_sn;
        private String goods_num;
        private String final_price;
        private String member_goods_price;
        private String give_integral;
        private String item_id;
        private String spec_key_name;
        private GoodImg.GoodImgs goods;
        private List<OrderGoodBean> order_goods;

        public int getIs_invoice() {
            return is_invoice;
        }

        public void setIs_invoice(int is_invoice) {
            this.is_invoice = is_invoice;
        }

        public int getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(int invoice_type) {
            this.invoice_type = invoice_type;
        }

        public int getInvoice_title() {
            return invoice_title;
        }

        public void setInvoice_title(int invoice_title) {
            this.invoice_title = invoice_title;
        }

        public String getInvoice_email() {
            return invoice_email;
        }

        public void setInvoice_email(String invoice_email) {
            this.invoice_email = invoice_email;
        }

        public String getInvoice_mobile() {
            return invoice_mobile;
        }

        public void setInvoice_mobile(String invoice_mobile) {
            this.invoice_mobile = invoice_mobile;
        }

        public String getInvoice_name() {
            return invoice_name;
        }

        public void setInvoice_name(String invoice_name) {
            this.invoice_name = invoice_name;
        }

        public int getIs_extend_time() {
            return is_extend_time;
        }

        public void setIs_extend_time(int is_extend_time) {
            this.is_extend_time = is_extend_time;
        }

        public List<OrderGoodBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodBean> order_goods) {
            this.order_goods = order_goods;
        }

        public GoodImg.GoodImgs getGoods() {
            return goods;
        }

        public void setGoods(GoodImg.GoodImgs goods) {
            this.goods = goods;
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

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getMember_goods_price() {
            return member_goods_price;
        }

        public void setMember_goods_price(String member_goods_price) {
            this.member_goods_price = member_goods_price;
        }

        public String getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(String give_integral) {
            this.give_integral = give_integral;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getSpec_key_name() {
            return spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public int getCountry() {
            return country;
        }

        public void setCountry(int country) {
            this.country = country;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public int getTwon() {
            return twon;
        }

        public void setTwon(int twon) {
            this.twon = twon;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getShipping_code() {
            return shipping_code;
        }

        public void setShipping_code(String shipping_code) {
            this.shipping_code = shipping_code;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getPay_code() {
            return pay_code;
        }

        public void setPay_code(String pay_code) {
            this.pay_code = pay_code;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getInvoice_taxpayer() {
            return invoice_taxpayer;
        }

        public void setInvoice_taxpayer(String invoice_taxpayer) {
            this.invoice_taxpayer = invoice_taxpayer;
        }

        public String getInvoice_desc() {
            return invoice_desc;
        }

        public void setInvoice_desc(String invoice_desc) {
            this.invoice_desc = invoice_desc;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getShipping_price() {
            return shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price;
        }

        public String getUser_money() {
            return user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getIntegral_money() {
            return integral_money;
        }

        public void setIntegral_money(String integral_money) {
            this.integral_money = integral_money;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(int shipping_time) {
            this.shipping_time = shipping_time;
        }

        public int getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(int confirm_time) {
            this.confirm_time = confirm_time;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public Object getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(Object transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getProm_id() {
            return prom_id;
        }

        public void setProm_id(int prom_id) {
            this.prom_id = prom_id;
        }

        public int getProm_type() {
            return prom_type;
        }

        public void setProm_type(int prom_type) {
            this.prom_type = prom_type;
        }

        public int getOrder_prom_id() {
            return order_prom_id;
        }

        public void setOrder_prom_id(int order_prom_id) {
            this.order_prom_id = order_prom_id;
        }

        public String getOrder_prom_amount() {
            return order_prom_amount;
        }

        public void setOrder_prom_amount(String order_prom_amount) {
            this.order_prom_amount = order_prom_amount;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getUser_note() {
            return user_note;
        }

        public void setUser_note(String user_note) {
            this.user_note = user_note;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public String getAdmin_note() {
            return admin_note;
        }

        public void setAdmin_note(String admin_note) {
            this.admin_note = admin_note;
        }

        public int getIs_distribut() {
            return is_distribut;
        }

        public void setIs_distribut(int is_distribut) {
            this.is_distribut = is_distribut;
        }

        public String getPaid_money() {
            return paid_money;
        }

        public void setPaid_money(String paid_money) {
            this.paid_money = paid_money;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getIs_cancel() {
            return is_cancel;
        }

        public void setIs_cancel(int is_cancel) {
            this.is_cancel = is_cancel;
        }

        public int getIs_receiving() {
            return is_receiving;
        }

        public void setIs_receiving(int is_receiving) {
            this.is_receiving = is_receiving;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public int getIs_return() {
            return is_return;
        }

        public void setIs_return(int is_return) {
            this.is_return = is_return;
        }

        public int getIs_complete() {
            return is_complete;
        }

        public void setIs_complete(int is_complete) {
            this.is_complete = is_complete;
        }

        public String getState_brief() {
            return state_brief;
        }

        public void setState_brief(String state_brief) {
            this.state_brief = state_brief;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public class GoodImg implements Serializable {
            private List<GoodImgs> goods_images;

            public List<GoodImgs> getGoods_images() {
                return goods_images;
            }

            public void setGoods_images(List<GoodImgs> goods_images) {
                this.goods_images = goods_images;
            }

            public class GoodImgs implements Serializable {
                private String img_id;
                private String image_url;
                private String original_img;

                public String getOriginal_img() {
                    return original_img;
                }

                public void setOriginal_img(String original_img) {
                    this.original_img = original_img;
                }

                public String getImg_id() {
                    return img_id;
                }

                public void setImg_id(String img_id) {
                    this.img_id = img_id;
                }

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }
            }
        }

        public class OrderGoodBean implements Serializable {

            /**
             * rec_id : 905
             * order_id : 959
             * goods_id : 68
             * goods_name : 圣女的救济 东野圭吾作品集 精装正版包邮 现当代日本文学悬疑推理侦探小说书籍 圣女的救赎 嫌疑人X的献身白夜行 新华书店文轩网
             * goods_num : 1
             * final_price : 0.00
             * goods_price : 1.00
             * cost_price : 0.00
             * member_goods_price : 1.00
             * item_id : 0
             * spec_key :
             * spec_key_name :
             * is_send : 0
             * goods : {"goods_id":68,"original_img":"http://api.qubuyer.comhttp://api.qubuyer.com/public/upload/goods/2019/04-08/f38d1e3acc1856683d3b073bbd23fa9a.jpg"}
             * comment_state : 1
             */

            private int rec_id;
            private int order_id;
            private int goods_id;
            private String goods_name;
            private int goods_num;
            private String final_price;
            private String goods_price;
            private String cost_price;
            private String member_goods_price;
            private int item_id;
            private String spec_key;
            private String spec_key_name;
            private int is_send;
            private GoodsBean goods;
            private int comment_state;
            private int is_return;
            private String original_img_full;

            public int getIs_return() {
                return is_return;
            }

            public void setIs_return(int is_return) {
                this.is_return = is_return;
            }

            public String getOriginal_img_full() {
                return original_img_full;
            }

            public void setOriginal_img_full(String original_img_full) {
                this.original_img_full = original_img_full;
            }

            public int getRec_id() {
                return rec_id;
            }

            public void setRec_id(int rec_id) {
                this.rec_id = rec_id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
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

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getFinal_price() {
                return final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getCost_price() {
                return cost_price;
            }

            public void setCost_price(String cost_price) {
                this.cost_price = cost_price;
            }

            public String getMember_goods_price() {
                return member_goods_price;
            }

            public void setMember_goods_price(String member_goods_price) {
                this.member_goods_price = member_goods_price;
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

            public int getIs_send() {
                return is_send;
            }

            public void setIs_send(int is_send) {
                this.is_send = is_send;
            }

            public GoodsBean getGoods() {
                return goods;
            }

            public void setGoods(GoodsBean goods) {
                this.goods = goods;
            }

            public int getComment_state() {
                return comment_state;
            }

            public void setComment_state(int comment_state) {
                this.comment_state = comment_state;
            }

            public class GoodsBean implements Serializable {
                /**
                 * goods_id : 68
                 * original_img : http://api.qubuyer.comhttp://api.qubuyer.com/public/upload/goods/2019/04-08/f38d1e3acc1856683d3b073bbd23fa9a.jpg
                 */

                private int goods_id;
                private String original_img;
                private List<GoodImgs> goods_images;

                public List<GoodImgs> getGoods_images() {
                    return goods_images;
                }

                public void setGoods_images(List<GoodImgs> goods_images) {
                    this.goods_images = goods_images;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getOriginal_img() {
                    return original_img;
                }

                public void setOriginal_img(String original_img) {
                    this.original_img = original_img;
                }

                public class GoodImgs implements Serializable {
                    private String original_img_full;

                    public String getOriginal_img_full() {
                        return original_img_full;
                    }

                    public void setOriginal_img_full(String original_img_full) {
                        this.original_img_full = original_img_full;
                    }
                }
            }
        }
    }
}
