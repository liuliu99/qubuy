package com.qubuyer.bean.good;

import java.io.Serializable;
import java.util.List;

/**
 * @date 创建时间:2019/3/25
 * @author Susong
 * @description 商品评论实体
 & @version
 */
public class GoodCommentEntity implements Serializable {


    /**
     * comment_id : 108
     * username : 噢噢噢哦哦111111
     * goods_rank : 5
     * head_img : http://down.qubuyer.com/public/upload/images/2019041518414d8e972e2041dd45a69d9a5f31fa.png
     * add_time_format : 2019-04-19 19:24
     * img_full : ["http://down.qubuyer.com/public/upload/images/2019041963a12bc477c73b6cf706db89d74b5c59file5cb9b001bf6db.jpg"]
     */

    private int comment_id;
    private String username;
    private int goods_rank;
    private String head_img;
    private String add_time_format;
    private List<String> img_full;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGoods_rank() {
        return goods_rank;
    }

    public void setGoods_rank(int goods_rank) {
        this.goods_rank = goods_rank;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getAdd_time_format() {
        return add_time_format;
    }

    public void setAdd_time_format(String add_time_format) {
        this.add_time_format = add_time_format;
    }

    public List<String> getImg_full() {
        return img_full;
    }

    public void setImg_full(List<String> img_full) {
        this.img_full = img_full;
    }
}
