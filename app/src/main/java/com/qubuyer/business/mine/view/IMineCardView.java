package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.MinePosterEntity;
import com.qubuyer.bean.mine.UserEntity;

public interface IMineCardView extends BaseView {
    void onShowQrCodeInfoToView(MineCardEntity entity);
    void onShowPosterInfoToView(MinePosterEntity entity);
    void onShowUserInfoToView(UserEntity entity);
}
