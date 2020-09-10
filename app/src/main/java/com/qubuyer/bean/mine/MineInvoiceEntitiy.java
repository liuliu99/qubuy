package com.qubuyer.bean.mine;

import java.io.Serializable;

/**
 * @author Susong
 * @date 创建时间2019/3/26
 * @description 发票信息实体
 */
public class MineInvoiceEntitiy implements Serializable {
    //是否开票 0:不开发票 1:开票
    private int isUse;
    //发票类型 1:个人 2:单位
    private int type;
    //收票人手机
    private String phone;
    //收票人邮箱
    private String email;
    //单位名称
    private String companyName;
    //单位税号
    private String companyDutyparagraph;
    //发票内容
    private String content;

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDutyparagraph() {
        return companyDutyparagraph;
    }

    public void setCompanyDutyparagraph(String companyDutyparagraph) {
        this.companyDutyparagraph = companyDutyparagraph;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
