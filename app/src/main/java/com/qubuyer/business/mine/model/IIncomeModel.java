package com.qubuyer.business.mine.model;


import com.qubuyer.base.mvp.BaseModel;

public interface IIncomeModel extends BaseModel {
    void loadAllData(final int loadType, String type);
}
