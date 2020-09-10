package com.qubuyer.bean.mine;

import java.io.Serializable;

/**
 * 收货地址实体
 */
public class MineAddressEntitiy implements Serializable {
    //收货地址ID(新增默认为0,编辑时加上收货地方ID)
    private String address_id;
    //收货人名称
    private String consignee;
    //邮箱
    private String email;
    //省
    private int province;
    //市
    private int city;
    //镇
    private int district;
    //街道
    private String twon;
    //地址
    private String address;
    //邮政编号
    private String zipcode;
    //手机号
    private String mobile;
    //是否默认：0否1是
    private int is_default;
    //省市区字符创
    private String address_area;

    public String getAddress_area() {
        return address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
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

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
