package com.qubuyer.business.good.view;

import android.view.View;

import com.qubuyer.base.mvp.BaseView;

public abstract class GoodDetailBaseViewPage implements BaseView {
    public abstract View getView();
    public abstract void loadData();
    public abstract void destory();
}


