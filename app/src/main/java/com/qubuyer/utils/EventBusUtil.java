package com.qubuyer.utils;

import com.qubuyer.bean.event.EventObj;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtil {
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void sendEvent(EventObj event) {
        EventBus.getDefault().post(event);
    }
}
