package com.qubuyer.business.mine.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IMessageListModel extends BaseModel {
    void getMessageList(int loadType, int type);
}
