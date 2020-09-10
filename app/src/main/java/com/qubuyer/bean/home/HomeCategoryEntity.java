package com.qubuyer.bean.home;

import java.io.Serializable;

public class HomeCategoryEntity implements Serializable {
    //分类ID
    private int id;
    //分类名
    private String name;
    //分类图片
    private String ico;

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

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
}
