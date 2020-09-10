package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IMessageDetailModel extends BaseModel {
    void getMessageDetailInfo(int messgeId);
}
