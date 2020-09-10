package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;

public interface IMineView extends BaseView {
    void onShowUserInfoToView(UserEntity entity);
    void onShowLoginOutResultToView(boolean result);
    void onShowUserMessageCountDataToView(UserMessageCountEntity entity);
    void onShoWalletInfoToView(WalletInfoEntity entity);
}
