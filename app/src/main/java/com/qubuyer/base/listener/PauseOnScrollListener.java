package com.qubuyer.base.listener;

import android.content.Context;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @date 创建时间:2019/1/9
 * @author Susong
 * @description 滚动时暂停线程任务 (仅用于RecylerView)
 & @version
 */
public class PauseOnScrollListener extends RecyclerView.OnScrollListener {

    private Context mContext;
    public PauseOnScrollListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE: {
                Glide.with(mContext).resumeRequests();
                break;
            }
            case RecyclerView.SCROLL_STATE_DRAGGING: {
                Glide.with(mContext).pauseRequests();
                break;
            }
            case RecyclerView.SCROLL_STATE_SETTLING: {
                Glide.with(mContext).pauseRequests();
                break;
            }
        }
    }
}
