package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

import java.io.File;

public interface IMineSettingModel extends BaseModel {
    void loginOut();
    void updateHeadImg(File file);
    void getUserInfo();
}
