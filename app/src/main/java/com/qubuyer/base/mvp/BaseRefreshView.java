package com.qubuyer.base.mvp;

public interface BaseRefreshView extends BaseView {
    void finishLoadMore(int delay, boolean success, boolean noMoreData);

    void finishRefresh(boolean success);
}
