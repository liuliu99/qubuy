package com.qubuyer.bean.event;

import com.qubuyer.bean.mine.MineInvoiceEntitiy;

/**
 * @date 创建时间:2019/1/24
 * @author Susong
 * @description 提交订单页选择发票事件类
 & @version
 */
public class SOSelectInvoiceEvent extends EventObj {
    private MineInvoiceEntitiy entitiy;

    public SOSelectInvoiceEvent() {
    }



    public SOSelectInvoiceEvent(MineInvoiceEntitiy entitiy) {
        this.entitiy = entitiy;
    }

    public MineInvoiceEntitiy getEntitiy() {
        return entitiy;
    }

    public void setEntitiy(MineInvoiceEntitiy entitiy) {
        this.entitiy = entitiy;
    }
}
