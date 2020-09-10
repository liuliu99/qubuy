package com.qubuyer.business.login.view;

import android.view.View;

import com.qubuyer.base.mvp.BaseView;

public abstract class LoginBaseViewPage implements BaseView {
    public abstract View getView();
    public abstract void destory();
}


