package com.qubuyer.base.mvp;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void doResponseError(int code, String message);
}
