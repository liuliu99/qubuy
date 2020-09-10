package com.qubuyer.bean.mine;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/4/1
 * @author Susong
 * @description 用户二维码信息
 & @version
 */
public class MineCardEntity extends Entity {

    /**
     * user_id : 292
     * origin_qrcode : public/upload/code/cost/292/68235d6bf9da87d5299eda6d03a99eec.png
     * push_id : U00292
     */

    private int user_id;
    private String origin_qrcode_full;
    private String push_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrigin_qrcode_full() {
        return origin_qrcode_full;
    }

    public void setOrigin_qrcode_full(String origin_qrcode_full) {
        this.origin_qrcode_full = origin_qrcode_full;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }
}
