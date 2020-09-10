package com.qubuyer.bean.order;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2019/3/31
 * @description 退款原因实体
 * @version
 */
public class OrderRefundReasonEntity extends Entity {

    /**
     * id : 7
     * name : 商品与页面描述不符
     */

    private int id;
    private String name;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
