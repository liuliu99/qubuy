package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;

public interface IWithdrawView extends BaseView {
    void onShoWalletInfoToView(WalletInfoEntity entity);
    void onShowUserInfoToView(UserEntity entity);
    void onShowVerificationcodeResultToView(boolean result);
    void onShowWithdrawResultToView(boolean result);
}
