package com.qubuyer.bean.order;

import com.qubuyer.bean.Entity;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 订单提交评论参数类实体
 * @version
 */
public class OrderCommentSubmitParamEntity extends Entity {
    private String orderId;
    private String rec_id;
    private String rank;
    private String anonymous;
    private String content;
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
