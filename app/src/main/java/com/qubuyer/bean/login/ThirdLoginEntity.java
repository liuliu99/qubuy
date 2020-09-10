package com.qubuyer.bean.login;

import java.io.Serializable;

public class ThirdLoginEntity implements Serializable {

    /**
     * openid : oMOQU5vJbc5SYdawNFygAyP05twE
     * oauth : wx
     */

    private String openid;
    private String oauth;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }
}
