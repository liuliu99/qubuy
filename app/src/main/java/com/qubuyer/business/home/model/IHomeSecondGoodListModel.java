package com.qubuyer.business.home.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IHomeSecondGoodListModel extends BaseModel {
    void getGoodList(int loadType, int type);
}
