package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2019/6/23
 * @description 收支明细实体类
 * @version
 */
public class IncomeEntity extends Entity {
    private String money;
    private String remark;
    private String desc;
    private int type;
    private long create_time;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
