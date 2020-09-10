package com.qubuyer.bean.mine;

import java.io.Serializable;

/**
 * @author Susong
 * @date 创建时间2019/3/15
 * @description 用户信息实体
 * @version
 */
public class UserEntity implements Serializable {
    //数据
    private int user_id;
    //邮箱
    private String email;
    //性别 0:保密 1:男 2:女
    private int sex;
    //会员等级
    private String level_name;
    //生日
    private int birthday;
    //折让金
    private String restore_money;
    //用户奖金
    private String bouns_money;
    //余额
    private String user_money;
    //佣金
    private String distribut_money;
    //积分
    private int pay_points;
    //电话
    private String mobile;

    private String openid;
    //头像
    private String head_pic;
    //用户名
    private String nickname;
    //
    private String total_amount;
    //收藏总数
    private int goods_collect_count;
    //
    private int goods_visit_count;
    //订单信息
    private OrderBean order;

    //是否绑定支付宝1是0否
    private int is_bind_alipay;
    //是否绑定微信1是0否
    private int is_bind_wx;
    //是否是消费商1是0否
    private int is_distribut;
    //提现比例
    private String withdrawal_cash;
    //邀请人
    private String second_leader_nickname;

    public String getSecond_leader_nickname() {
        return second_leader_nickname;
    }

    public void setSecond_leader_nickname(String second_leader_nickname) {
        this.second_leader_nickname = second_leader_nickname;
    }

    public String getWithdrawal_cash() {
        return withdrawal_cash;
    }

    public void setWithdrawal_cash(String withdrawal_cash) {
        this.withdrawal_cash = withdrawal_cash;
    }

    public String getRestore_money() {
        return restore_money;
    }

    public void setRestore_money(String restore_money) {
        this.restore_money = restore_money;
    }

    public String getBouns_money() {
        return bouns_money;
    }

    public void setBouns_money(String bouns_money) {
        this.bouns_money = bouns_money;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public int getIs_bind_alipay() {
        return is_bind_alipay;
    }

    public void setIs_bind_alipay(int is_bind_alipay) {
        this.is_bind_alipay = is_bind_alipay;
    }

    public int getIs_bind_wx() {
        return is_bind_wx;
    }

    public void setIs_bind_wx(int is_bind_wx) {
        this.is_bind_wx = is_bind_wx;
    }

    public int getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(int is_distribut) {
        this.is_distribut = is_distribut;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getDistribut_money() {
        return distribut_money;
    }

    public void setDistribut_money(String distribut_money) {
        this.distribut_money = distribut_money;
    }

    public int getPay_points() {
        return pay_points;
    }

    public void setPay_points(int pay_points) {
        this.pay_points = pay_points;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getGoods_collect_count() {
        return goods_collect_count;
    }

    public void setGoods_collect_count(int goods_collect_count) {
        this.goods_collect_count = goods_collect_count;
    }

    public int getGoods_visit_count() {
        return goods_visit_count;
    }

    public void setGoods_visit_count(int goods_visit_count) {
        this.goods_visit_count = goods_visit_count;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean implements Serializable{
        //待支付
        private int wait_pay_count;
        //待发货
        private int wait_unsend_count;
        //待收货
        private int wait_send_count;
        //完成订单数（包含待评介订单）
        private int wait_receive_count;
        //售后订单数
        private int return_count;

        public int getWait_unsend_count() {
            return wait_unsend_count;
        }

        public void setWait_unsend_count(int wait_unsend_count) {
            this.wait_unsend_count = wait_unsend_count;
        }

        public int getWait_pay_count() {
            return wait_pay_count;
        }

        public void setWait_pay_count(int wait_pay_count) {
            this.wait_pay_count = wait_pay_count;
        }

        public int getWait_send_count() {
            return wait_send_count;
        }

        public void setWait_send_count(int wait_send_count) {
            this.wait_send_count = wait_send_count;
        }

        public int getWait_receive_count() {
            return wait_receive_count;
        }

        public void setWait_receive_count(int wait_receive_count) {
            this.wait_receive_count = wait_receive_count;
        }

        public int getReturn_count() {
            return return_count;
        }

        public void setReturn_count(int return_count) {
            this.return_count = return_count;
        }
    }
}
