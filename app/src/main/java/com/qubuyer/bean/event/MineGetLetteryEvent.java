package com.qubuyer.bean.event;

/**
 * @author Susong
 * @date 创建时间2020/1/11
 * @description 领奖记录领奖事件类
 */
public class MineGetLetteryEvent extends EventObj {
    private String province, city, district, consignee, mobile, address;

    public MineGetLetteryEvent(String province, String city, String district, String consignee, String mobile, String address) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.consignee = consignee;
        this.mobile = mobile;
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
