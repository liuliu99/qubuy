package com.qubuyer.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public abstract class HandlerUtils<T> extends Handler {
    protected final WeakReference<Context> mReference;
    protected final WeakReference<T> mReferenceT;

    public HandlerUtils(Context context, T t) {
        mReference = new WeakReference<>(context);
        mReferenceT = new WeakReference<>(t);
    }

    /**
     * 执行体
     *
     * @param t
     */
    public abstract void execute(T t);

    private Runnable innerRun = new Runnable() {

        @Override
        public void run() {
            Context context = mReference.get();
            if (context != null) {
                if (context instanceof Activity && ((Activity) context).isFinishing()) {
                    release();
                    Logger.w("The timer executed, but " + context.getClass().getSimpleName() + " is finishing!");
                } else {
                    T t = mReferenceT.get();
                    if (t != null) {
                        execute(t);
                        Logger.d("The timer executed in " + context.getClass().getName());
                    } else {
                        Logger.w("The timer executed, but callback class is null");
                    }
                }
            } else {
                release();
                Logger.w("The timer executed, but activity destoryed!");
            }
        }
    };

    /**
     * 暂停任务
     */
    public void stop() {
        removeCallbacks(innerRun);
    }

    /**
     * 销毁任务
     */
    public void release() {
        stop();
        mReference.clear();
        mReferenceT.clear();
    }

    /**
     * 延迟执行
     *
     * @param time 延迟时间
     */
    public void postDelayed(long time) {
        stop();
        postDelayed(innerRun, time);
    }
}
