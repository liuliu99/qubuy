package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @author Susong
 * @date 创建时间2020/1/10
 * @description 我的奖品记录实体
 * @version
 */
public class MineLotteryRecordEntity extends Entity {


    /**
     * name : 谢谢惠顾
     * is_get : 1
     * main_image_full_path : http://down.qubuyer.com/public/upload/images/20200108a018d86aaa2c4f8e373dabc80a978d63file5e1595c5785d0.jpg
     */

    private String id;
    private String name;
    private int is_get;
    private String main_image_full_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_get() {
        return is_get;
    }

    public void setIs_get(int is_get) {
        this.is_get = is_get;
    }

    public String getMain_image_full_path() {
        return main_image_full_path;
    }

    public void setMain_image_full_path(String main_image_full_path) {
        this.main_image_full_path = main_image_full_path;
    }
}
