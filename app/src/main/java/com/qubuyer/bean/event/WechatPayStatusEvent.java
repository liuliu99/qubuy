package com.qubuyer.bean.event;

/**
 * @date 创建时间:2019/3/19
 * @author Susong
 * @description 微信支付状态
 & @version
 */
public class WechatPayStatusEvent extends EventObj {
    //支付状态 1:成功 2:失败 3:取消
    private int status;

    public WechatPayStatusEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
