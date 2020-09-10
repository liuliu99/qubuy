package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/4/1
 * @author Susong
 * @description 钱包信息实体
 & @version
 */
public class WalletInfoEntity extends Entity {

    /**
     * money : 0.00
     * restore : 0
     * distribut : 3081
     */

    private String money;
    private String restore;
    private String distribut;
    private String bonus;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

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
