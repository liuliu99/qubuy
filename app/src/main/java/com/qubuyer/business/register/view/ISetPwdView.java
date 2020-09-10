package com.qubuyer.business.register.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.register.RegisterEntity;

public interface ISetPwdView extends BaseView {
    void onShowSetPwdResultToView(RegisterEntity entity);
}
