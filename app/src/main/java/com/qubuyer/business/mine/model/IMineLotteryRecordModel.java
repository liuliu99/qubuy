package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IMineLotteryRecordModel extends BaseModel {
    void getMineLotteryRecordList();
    void getLottery(String id, String consignee, String mobile, String province, String city, String district, String address);
}
