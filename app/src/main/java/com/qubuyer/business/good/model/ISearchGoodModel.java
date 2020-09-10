package com.qubuyer.business.good.model;


import com.qubuyer.base.mvp.BaseModel;

public interface ISearchGoodModel extends BaseModel {
    void loadSearchResultFirstList(String key);
}
