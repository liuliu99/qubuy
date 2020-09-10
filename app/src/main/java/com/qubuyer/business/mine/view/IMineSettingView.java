package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.UserEntity;

public interface IMineSettingView extends BaseView {
    void onShowLoginOutResultToView(boolean result);
    void onShowUpdateUserHeadImgResultToView(boolean result);
    void onShowUserInfoToView(UserEntity entity);
}
