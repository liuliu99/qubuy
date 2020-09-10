package com.qubuyer.bean.order;

import java.io.Serializable;

public class InvoiceInfo implements Serializable {
    //状态：0待审核1审核通过-1审核不通过
    private String status;
    //发票税号
    private String invoice_taxpayer;
    //发票手机号
    private String invoice_mobile;
    //发票邮箱
    private String invoice_email;
    //发票抬头1个人2单位，如果是1则展示个人，如果是2则展示invoice_name字段
    private String invoice_title;
    //发票公司名
    private String invoice_name;
    //发票描述
    private String invoice_desc;
    //发票类型0不开发票1电子票
    private String invoice_type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoice_taxpayer() {
        return invoice_taxpayer;
    }

    public void setInvoice_taxpayer(String invoice_taxpayer) {
        this.invoice_taxpayer = invoice_taxpayer;
    }

    public String getInvoice_mobile() {
        return invoice_mobile;
    }

    public void setInvoice_mobile(String invoice_mobile) {
        this.invoice_mobile = invoice_mobile;
    }

    public String getInvoice_email() {
        return invoice_email;
    }

    public void setInvoice_email(String invoice_email) {
        this.invoice_email = invoice_email;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public String getInvoice_desc() {
        return invoice_desc;
    }

    public void setInvoice_desc(String invoice_desc) {
        this.invoice_desc = invoice_desc;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }
}
