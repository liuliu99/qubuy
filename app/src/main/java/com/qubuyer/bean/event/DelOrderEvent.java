package com.qubuyer.bean.event;

import com.qubuyer.bean.mine.MineInvoiceEntitiy;

/**
 * @author Susong
 * @date 创建时间2020/6/7
 * @description 删除订单事件类
 * @version
 */
public class DelOrderEvent extends EventObj {
    private MineInvoiceEntitiy entitiy;

    public DelOrderEvent() {
    }



    public DelOrderEvent(MineInvoiceEntitiy entitiy) {
        this.entitiy = entitiy;
    }

    public MineInvoiceEntitiy getEntitiy() {
        return entitiy;
    }

    public void setEntitiy(MineInvoiceEntitiy entitiy) {
        this.entitiy = entitiy;
    }
}
