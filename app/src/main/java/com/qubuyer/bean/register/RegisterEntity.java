package com.qubuyer.bean.register;

import java.io.Serializable;

public class RegisterEntity implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
