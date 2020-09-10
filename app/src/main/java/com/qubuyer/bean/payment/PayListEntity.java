package com.qubuyer.bean.payment;

import java.io.Serializable;

/**
 * @date 创建时间:2019/1/22
 * @author Susong
 * @description 支付列表实体
 & @version
 */
public class PayListEntity implements Serializable {
    private boolean isSelected;
    private String typeImg;
    private String typeContent;
    // 1:微信 2:支付宝 3:银联
    private int type;
    private boolean isSpecial;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(String typeImg) {
        this.typeImg = typeImg;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }
}
