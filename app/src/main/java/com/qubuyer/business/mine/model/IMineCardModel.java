package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IMineCardModel extends BaseModel {
    void getQrCode();
    void getPoster();
    void getUserInfo();
}
