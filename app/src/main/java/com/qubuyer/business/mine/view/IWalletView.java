package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;

public interface IWalletView extends BaseView {
    void onShoWalletInfoToView(WalletInfoEntity entity);
}
