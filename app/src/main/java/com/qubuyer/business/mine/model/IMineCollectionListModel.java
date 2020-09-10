package com.qubuyer.business.mine.model;

import com.qubuyer.base.mvp.BaseModel;
import com.qubuyer.bean.mine.MineCollectEntity;

public interface IMineCollectionListModel extends BaseModel {
    void loadCollectionListData();
    void deleteCollection(MineCollectEntity entity);
}
