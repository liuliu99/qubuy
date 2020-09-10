package com.qubuyer.bean.event;

import com.qubuyer.bean.mine.MineAddressEntitiy;

/**
 * @date 创建时间:2019/1/24
 * @author Susong
 * @description 提交订单页选择地址事件类
 & @version
 */
public class SOSelectAddressEvent extends EventObj {
    private MineAddressEntitiy entitiy;

    public SOSelectAddressEvent() {
    }

    public SOSelectAddressEvent(MineAddressEntitiy entitiy) {
        this.entitiy = entitiy;
    }

    public MineAddressEntitiy getEntitiy() {
        return entitiy;
    }

    public void setEntitiy(MineAddressEntitiy entitiy) {
        this.entitiy = entitiy;
    }
}
