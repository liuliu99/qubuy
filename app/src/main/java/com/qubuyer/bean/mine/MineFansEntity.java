package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 我的粉丝实体
 * @version
 */
public class MineFansEntity extends Entity {
    private int user_id;
    private String head_pic;
    private long reg_time;
    private String nickname;
    private float money;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public long getReg_time() {
        return reg_time;
    }

    public void setReg_time(long reg_time) {
        this.reg_time = reg_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
