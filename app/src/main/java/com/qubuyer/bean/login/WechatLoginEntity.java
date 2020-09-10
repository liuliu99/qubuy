package com.qubuyer.bean.login;

import com.qubuyer.bean.Entity;

public class WechatLoginEntity extends Entity {

    /**
     * country : 中国
     * unionid : oCYpbv2n1MLV6_ycitZMFNIfCjc0
     * gender : 1
     * city : 武汉
     * openid : o0movvwvUi8JlJR43rdqT8Gg0ojA
     * icon : http://thirdwx.qlogo.cn/mmopen/vi_32/3tmqrh93SJ1cbzicMvxssqjYKZcoFkUDkzlsbNBhVC9AtOM2XXsat5ov27ED8Dsc2K7ficMWic3RyDQEd1OJqgf8w/132
     * userID : o0movvwvUi8JlJR43rdqT8Gg0ojA
     * expiresTime : 1578751490578
     * token : 29_-sWI5pRCcWQIFTwM2GhidmtpGumXSQV656l1ruSgRoXoB2SmLAwgpZA6WtnEBQ-PA9oz_NjqtrRKSbB_6qFuJjSFUowpIatVOcqeq-CtaS0
     * expiresIn : 7200
     * refresh_token : 29_fSfPhrrkZyqpk11IliDgOAFh7FOFWU6Lye2RhWOaHsJvEBjOduQCctlmvUrT_z6jCLMAyzkNSdy_4wlyPg2zfQU6uh0V7maoKohzWGdm-5k
     * province : 湖北
     * nickname : 小迷弟😁
     */

    private String country;
    private String unionid;
    private String gender;
    private String city;
    private String openid;
    private String icon;
    private String userID;
    private long expiresTime;
    private String token;
    private int expiresIn;
    private String refresh_token;
    private String province;
    private String nickname;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
