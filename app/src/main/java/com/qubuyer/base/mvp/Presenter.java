package com.qubuyer.base.mvp;

public interface Presenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

    void attachModel(BaseModel model);

    void destoryModel();
    // 释放资源的操作
    void destroy();
}
