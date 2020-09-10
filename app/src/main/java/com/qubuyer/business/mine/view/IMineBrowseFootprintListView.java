package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineBrowseFootprintEntity;

import java.util.List;

public interface IMineBrowseFootprintListView extends BaseView {
    void onShowBrowseFootprintListToView(List<MineBrowseFootprintEntity.ValueBean> list);
    void onDeleteBrowseFootprintResult(boolean success);
    void onClearBrowseFootprintResult(boolean success);
}
