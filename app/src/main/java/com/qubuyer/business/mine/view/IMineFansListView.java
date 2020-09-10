package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineFansEntity;

import java.util.List;

public interface IMineFansListView extends BaseView {
    void onShowFansListToView(List<MineFansEntity> list);
}
