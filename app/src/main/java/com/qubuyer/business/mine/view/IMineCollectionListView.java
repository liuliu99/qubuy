package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineCollectEntity;

import java.util.List;

public interface IMineCollectionListView extends BaseView {
    void onShowCollectListToView(List<MineCollectEntity> list);
    void onDeleteCollectionResult(boolean success);
}
