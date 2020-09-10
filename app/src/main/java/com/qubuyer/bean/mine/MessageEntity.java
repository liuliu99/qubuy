package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/4/15
 * @author Susong
 * @description 活动消息列表实体
 & @version
 */
public class MessageEntity extends Entity {

    /**
     * id : 22
     * title : 123
     * image : null
     */

    private int id;
    private String title;
    private Object image;
    private String content;
    private String create_time;
    //订单ID(子订单ID)
    private String order_id;
    //主订单ID
    private String master_id;

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }
}
