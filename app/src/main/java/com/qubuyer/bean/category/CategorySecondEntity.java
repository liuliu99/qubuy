package com.qubuyer.bean.category;

import java.io.Serializable;

/**
 * @author Susong
 * @date 创建时间2019/3/11
 * @description 分类实体
 * @version
 */
public class CategorySecondEntity implements Serializable {
    //商品id
    private int id;
    //PC端商品名
    private String name;
    //手机端商品名
    private String mobile_name;
    //父ID
    private int parent_id;
    //商品图片
    private String image;
    //	是否热门
    private int is_hot;
    private int commission_rate;
    private int selected;

    //分类banner图路径
    private String banner_image_full_path;
    //分类banner图商品ID
    private Integer banner_image_goods_id;

    public String getBanner_image_full_path() {
        return banner_image_full_path;
    }

    public void setBanner_image_full_path(String banner_image_full_path) {
        this.banner_image_full_path = banner_image_full_path;
    }

    public Integer getBanner_image_goods_id() {
        return banner_image_goods_id;
    }

    public void setBanner_image_goods_id(Integer banner_image_goods_id) {
        this.banner_image_goods_id = banner_image_goods_id;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

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

    public String getMobile_name() {
        return mobile_name;
    }

    public void setMobile_name(String mobile_name) {
        this.mobile_name = mobile_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(int commission_rate) {
        this.commission_rate = commission_rate;
    }
}
