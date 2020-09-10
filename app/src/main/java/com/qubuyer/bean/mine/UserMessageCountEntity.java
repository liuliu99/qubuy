package com.qubuyer.bean.mine;

import java.io.Serializable;
/**
 * @date 创建时间:2019/3/18
 * @author Susong
 * @description 用户未读消息数量实体类
 & @version
 */
public class UserMessageCountEntity implements Serializable {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
