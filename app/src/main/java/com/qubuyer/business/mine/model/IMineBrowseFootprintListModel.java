package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;

public interface IMineBrowseFootprintListModel extends BaseModel {
    void loadBrowseFootprintListData();
    void deleteBrowseFootprint(String id);
    void clearBrowseFootprint();
}
