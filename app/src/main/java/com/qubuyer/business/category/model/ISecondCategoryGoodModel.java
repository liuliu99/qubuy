package com.qubuyer.business.category.model;

import com.qubuyer.base.mvp.BaseModel;

public interface ISecondCategoryGoodModel extends BaseModel {
    void loadData(int loadType, String firstCategoryId, String secondCategoryId);
}
