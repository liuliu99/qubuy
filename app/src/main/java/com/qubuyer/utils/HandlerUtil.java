package com.qubuyer.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @date 创建时间:2018/12/19
 * @author Susong
 * @description handler工具类
 & @version
 */
public class HandlerUtil extends Handler {
    private HandleMsgListener listener;
    private String Tag = HandlerUtil.class.getSimpleName();

    private static class Holder {
        private static final HandlerUtil HANDLER = new HandlerUtil();
    }

    public static HandlerUtil getInstance() {
        return Holder.HANDLER;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (getHandleMsgListener() != null) {
            getHandleMsgListener().handleMsg(msg);
        } else {
            Log.e(Tag, "请传入HandleMsgListener对象");
        }
    }

    public void setHandleMsgListener(HandleMsgListener listener) {
        this.listener = listener;
    }

    public HandleMsgListener getHandleMsgListener() {
        return listener;
    }

    public interface HandleMsgListener {
        void handleMsg(Message msg);
    }
}
