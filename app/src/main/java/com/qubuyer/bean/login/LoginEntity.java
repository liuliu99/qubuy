package com.qubuyer.bean.login;

import java.io.Serializable;

public class LoginEntity implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
