package com.qubuyer.base.mvp;

import java.util.List;

public interface IPresenter<T> {
    void onLoadDataEmpty(int loadType);
    void onLoadDataFailed(int loadType, String dataEmptyMsg);
    void onLoadDataSuccess(List<T> t, int loadType);
}
