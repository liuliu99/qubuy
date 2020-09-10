package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间:2019/3/18
 * @description 用户钱包信息
 * & @version
 */
public class UserWalletEntity extends Entity {
    //用户余额
    private String money;
    //预计折让金
    private String restore;

    //预计佣金
    private String distribut;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRestore() {
        return restore;
    }

    public void setRestore(String restore) {
        this.restore = restore;
    }

    public String getDistribut() {
        return distribut;
    }

    public void setDistribut(String distribut) {
        this.distribut = distribut;
    }
}
